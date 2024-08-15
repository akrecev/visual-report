package com.rokdc.report.dao;

import com.rokdc.report.exception.NoSuchElement;
import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.log.LogWriter;
import com.rokdc.report.model.Element;
import com.rokdc.report.model.ElementData;
import com.rokdc.report.model.Page;
import com.rokdc.report.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ElementDao implements IElementDao<Element> {

    private final JdbcTemplate jdbcTemplate;
    private final LogWriter log;


    public ElementDao(@Autowired LogWriter log, @Autowired JdbcTemplate jdbcTemplate) {
        super();
        this.log = log;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<ElementData> getData(String queryName, List<String> filterParameters) {
        ElementData data = new ElementData();
        try {
            final Query query = new Query(queryName);

            final LinkedHashMap<String, LinkedList<String>> result = jdbcTemplate.query(
                    query.getSqlText()
                    , new ElementPreparedStatementSetter(filterParameters, log)
                    , new ElementResultSetExtractor());

            data.setData(result);

            return Optional.of(data);

        } catch (FileNotFoundException e) {
            log.writeError("Не удалось прочитать файл: " + queryName);
            return Optional.ofNullable(data);
        }

    }

    @Override
    public Element getElement(Element element, Page page) throws NoSuchElement {
        List<Element> elements = page.getElements();

        if (page.getElements().contains(element)) {
            int pos = elements.indexOf(element);

            return Optional.
                    of(elements.get(pos))
                    .orElseThrow(() -> new NoSuchElement("Элемент: " + element.getId() + " не найден на странице " + page.getName()));
        } else throw new NoSuchElement("Элемент: " + element.getId() + " не найден на странице " + page.getName());

    }


    @Override
    public Element getElementById(String elementID, Page page) throws NoSuchElement, PropertiesLoadException {

        List<Element> elements = page.getElements();

        List<Element> elementsFound = elements.stream().filter(x -> x.getId().equals(elementID)).collect(Collectors.toList());

        if (elementsFound.size() > 1) {
            throw new PropertiesLoadException("На странице обнаружен элемент с неуникальным id");
        }

        return elementsFound.get(0);
    }


}

package com.rokdc.report.service;

import com.rokdc.report.dao.IElementDao;
import com.rokdc.report.dao.IPageDao;
import com.rokdc.report.dao.PageDao;
import com.rokdc.report.exception.NoSuchElement;
import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.model.App;
import com.rokdc.report.model.Element;
import com.rokdc.report.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PageService {
    private App app;
    private final IPageDao<Page> pageDao;
    private final IElementDao<Element> elementDao;


    public PageService(@Autowired PageDao pageDao, @Autowired IElementDao<Element> elementDao) throws PropertiesLoadException, URISyntaxException, IOException {
        this.pageDao = pageDao;
        this.elementDao = elementDao;
    }


    public ArrayList<Page> setPagesFromProperties() throws PropertiesLoadException, URISyntaxException, IOException {
        try {
            final Optional<List<Page>> opPages = pageDao.getAll();

            if (opPages.isPresent()) {
                final ArrayList<Page> pages = (ArrayList<Page>) opPages.get();

                return pages;

            } else throw new PropertiesLoadException("Отстутствуют параметры страниц");
        } catch (PropertiesLoadException ex) {
            throw new PropertiesLoadException(ex.getMessage() + ex.getCause());
        }

    }


    public Page get(String pageName) throws PropertiesLoadException {
        final ArrayList<Page> pages = app.getPages();

        return pages.stream()
                .filter(x -> x.getName().toLowerCase().equals(pageName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new PropertiesLoadException("Страница не найдена"));
    }

    public void fillPageData(final Page page, final List<String> filterParameters) {
        List<Element> elements = page.getElements();

        for (Element el : elements) {
            /*if (el instanceof Table) {
                Table t = ((Table) el);
                if (t.getData() != null) {
                    t.getData().getData().clear();
                }
            } else if (el instanceof Chart) {
                Chart c = ((Chart) el);
                if (c != null) {
                    c.getChartData().clear();
                }
            }*/
            el.getType().fill(el, new ArrayList<String>(), (p, f) -> elementDao.getData(p, f = filterParameters));

        }
    }

    public Element updateElement(final String elementID, final Page page, final List<String> filterParameters1) throws NoSuchElement, PropertiesLoadException {
        final Element elementForUpdate = elementDao.getElementById(elementID, page);

        elementForUpdate.getType()
                .fill(elementForUpdate, new ArrayList<String>(), (p, f) -> elementDao.getData(p, f = filterParameters1));

        System.out.println(elementForUpdate);

        return elementForUpdate;

    }

    public App getApp() {
        return app;
    }


    public void setApp() throws PropertiesLoadException, URISyntaxException, IOException {
        this.app = new App(setPagesFromProperties());
    }


}

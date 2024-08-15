package com.rokdc.report.dao;

import com.rokdc.report.exception.NoSuchElement;
import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.model.Element;
import com.rokdc.report.model.ElementData;
import com.rokdc.report.model.Page;

import java.util.List;
import java.util.Optional;

public interface IElementDao<T extends Element> {
    Element getElement(T t, Page page) throws NoSuchElement;

    Element getElementById(String elementID, Page page) throws NoSuchElement, PropertiesLoadException;

    Optional<ElementData> getData(String queryString, List<String> parameters);


}

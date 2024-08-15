package com.rokdc.report.dao;

import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.model.Page;

import java.util.List;
import java.util.Optional;

public interface IPageDao<T> {
    Optional<List<T>> getAll() throws PropertiesLoadException;

    Optional<Page> get(String name) throws PropertiesLoadException;

}

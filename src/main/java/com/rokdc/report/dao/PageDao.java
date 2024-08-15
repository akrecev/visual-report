package com.rokdc.report.dao;

import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.model.App;
import com.rokdc.report.model.Page;
import com.rokdc.report.utils.Properties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PageDao implements IPageDao<Page> {

    @Override
    public Optional<List<Page>> getAll() throws PropertiesLoadException {
        return Optional.ofNullable(Properties.deserialize(App.class).getPages());
    }

    @Override
    public Optional<Page> get(String name) throws PropertiesLoadException {
        Optional<List<Page>> opPages = this.getAll();

        if (opPages.isPresent()) {
            Optional<Page> result = Optional.empty();

            for (Page p : opPages.get()) {
                if (p.getName().equals(name)) {
                    result = Optional.of(p);
                }

            }
            return result;
        } else {
            throw new PropertiesLoadException("");
        }

    }


}

package com.rokdc.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Page {
    private final String name;
    private final int maxRows;
    private final int maxColumns;
    private final List<Element> elements;

    @JsonCreator
    public Page(@JsonProperty("name") String name
            , @JsonProperty("elements") List<Element> elements
            , @JsonProperty("maxRows") int maxRows
            , @JsonProperty("maxColumns") int maxColumns) {
        super();
        this.name = name;
        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        this.elements = elements;
    }


    public String getName() {
        return name;
    }


    public List<Element> getElements() {
        return elements;
    }


    @Override
    public String toString() {
        return name;

    }


    public int getMaxColumns() {
        return maxColumns;
    }


    public int getMaxRows() {
        return maxRows;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }


        Page page = (Page) obj;

        return page.getName().equals(this.name);
    }


}

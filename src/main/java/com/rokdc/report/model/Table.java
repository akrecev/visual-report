package com.rokdc.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Table extends Element {
    @JsonIgnore
    private final String datasource;
    private ElementData data;


    public Table(String id, String name, int row, int column, String type, long interval, String datasource) {
        super(id, name, row, column, type, interval);
        this.datasource = datasource;
    }


    public ElementData getData() {
        return data;
    }


    public void setData(ElementData data) {
        this.data = data;
    }


    public String getDatasource() {
        return datasource;
    }

}

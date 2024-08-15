package com.rokdc.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rokdc.report.exception.NoSuchValue;
import com.rokdc.report.utils.Tools;

import java.util.List;
import java.util.Map;


public abstract class Element {
    private final String id;
    private final String name;
    private final int row;
    private final int column;
    private final ElementType type;
    private final long interval;


    protected Element(String id, String name, int row, int column, String type, long interval) throws NoSuchValue {
        super();
        this.id = id;
        this.name = name;
        this.row = row;
        this.column = column;
        this.type = Tools.checkEnumValue(ElementType.class, type);
        this.interval = interval;
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getRow() {
        return row;
    }


    public int getColumn() {
        return column;
    }


    public ElementType getType() {
        return type;
    }


    public long getInterval() {
        return interval;
    }

    @JsonCreator
    public static Element create
            (
                    @JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("row") int row,
                    @JsonProperty("column") int column,
                    @JsonProperty("type") String type,
                    @JsonProperty("dataSource") String datasource,
                    @JsonProperty("chartData") Map<String, List<String>> chartData,
                    @JsonProperty("interval") long interval) throws NoSuchValue {
        return Tools.checkEnumValue(ElementType.class, type)
                .createElement(id, name, row, column, type, datasource, chartData, interval);
    }

    @Override
    public String toString() {
        return "id: " + id
                + " name: " + name
                + " row: " + row
                + " column: " + column
                + " type: " + type.toString();
    }


}

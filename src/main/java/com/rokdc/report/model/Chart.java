package com.rokdc.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rokdc.report.utils.Tools;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Chart extends Element {
    @JsonIgnore
    private final Map<ChartType, List<String>> chartData;
    private Map<ChartType, List<ElementData>> data;


    public Chart(String id, String name, int row, int column, String type
            , long interval
            , Map<String, List<String>> chartData) {
        super(id, name, row, column, type, interval);

        Map<ChartType, List<String>> chartDataWithType = new LinkedHashMap<>();

        chartData.forEach((k, v) -> chartDataWithType.put
                (
                        Tools.checkEnumValue(ChartType.class, k), v)
        );

        this.chartData = chartDataWithType;

        this.data = new HashMap<>();

        this.chartData.keySet().forEach((k) -> data.put(k, null));


    }

    public Map<ChartType, List<String>> getChartData() {
        return chartData;
    }

    public Map<ChartType, List<ElementData>> getData() {
        return data;
    }

    public void setData(Map<ChartType, List<ElementData>> data) {

        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString()).append(" ");
        data.forEach((k, v) -> result.append(k + " " + v));
        return result.toString();
    }


}



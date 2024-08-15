package com.rokdc.report.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public enum ElementType {
    CHART {
        @Override
        public Element createElement(String id, String name, int row, int column, String type,
                                     String datasource, Map<String, List<String>> chartData, long interval) {
            return new Chart(id, name, row, column, type, interval, chartData);
        }


        public Element fill(Element element, List<String> parameters, BiFunction<String, List<String>, Optional<ElementData>> function) {
            final Chart chart = (Chart) element;

            final Map<ChartType, List<String>> chartData = chart.getChartData();
            final Map<ChartType, List<ElementData>> data = chart.getData();

            data.clear();

            chartData.entrySet()
                    .forEach(
                            e -> data.putIfAbsent(e.getKey()
                                    , e.getValue().stream()
                                            .map(x -> function.apply(x, parameters))
                                            .filter(y -> y.isPresent())
                                            .map(z -> z.get())
                                            .collect(Collectors.toList())));

            chart.setData(data);

            return element;
        }


    },
    TABLE {
        @Override
        public Element createElement(String id, String name, int row, int column, String type,
                                     String datasource, Map<String, List<String>> chartData, long interval) {
            return new Table(id, name, row, column, type, interval, datasource);
        }

        @Override
        public Element fill(Element element, List<String> parameters,
                            BiFunction<String, List<String>, Optional<ElementData>> function) {
            final Table table = (Table) element;

            ElementData data = new ElementData();

            Optional<ElementData> optData = function.apply(table.getDatasource(), parameters);

            data.setData(optData.get().getData());

            table.setData(data);

            return element;
        }

    },
    FILTER {
        @Override
        public Element createElement(String id, String name, int row, int column, String type, String datasource,
                                     Map<String, List<String>> chartData, long interval) {

            return new Table(id, name, row, column, type, interval, datasource);
        }

        @Override
        public Element fill(Element element, List<String> parameters,
                            BiFunction<String, List<String>, Optional<ElementData>> function) {

            return TABLE.fill(element, parameters, function);
        }

    };

    public abstract Element createElement(String id, String name, int row, int column,
                                          String type, String datasource, Map<String, List<String>> chartData, long interval);

    public abstract Element fill(Element el
            , List<String> parameters
            , BiFunction<String, List<String>, Optional<ElementData>> function
    );

}

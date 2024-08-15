package com.rokdc.report.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ElementData {
    private LinkedHashMap<String, LinkedList<String>> data;


    public LinkedHashMap<String, LinkedList<String>> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, LinkedList<String>> data) {
        this.data = data;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (data != null) {
            data.keySet().forEach(k -> builder.append(k + ": " + data.get(k) + " "));
        }
        return builder.toString();
    }
}

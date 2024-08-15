package com.rokdc.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class App {
    private final ArrayList<Page> pages;


    @JsonCreator
    public App(@JsonProperty("pages") ArrayList<Page> pages) {
        super();
        this.pages = pages;
    }


    public ArrayList<Page> getPages() {
        return pages;
    }

}

package com.rokdc.report.controller;

import com.rokdc.report.exception.NoSuchElement;
import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.log.LogWriter;
import com.rokdc.report.model.Element;
import com.rokdc.report.model.Page;
import com.rokdc.report.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class DataController {

    private final PageService pageService;
    private final LogWriter log;
    private final String mainPage;

    public DataController(@Autowired PageService pageService
            , @Autowired LogWriter log
            , @Value("${settings.start-page}") String mainPage) {
        super();
        this.pageService = pageService;
        this.log = log;
        this.mainPage = mainPage;
    }

    @GetMapping("/api")
    public ResponseEntity<Page> getPagesSettings() throws PropertiesLoadException {
        Page page = pageService.get(mainPage);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/api/{name}")
    public ResponseEntity<Page> getCurrentPagesSettings(
            @PathVariable("name") String name
            , @RequestParam(value = "filter", required = false) Set<String> filter
    ) throws PropertiesLoadException {

        List<String> filterList = new ArrayList<>();

        if (filter != null) {
            filterList.addAll(filter);
        }

        //if(!name.equals(mainPage))--Обновлять только главную страницу
        //{
        pageService.fillPageData(pageService.get(name), filterList);
        //}

        return new ResponseEntity<Page>(pageService.get(name), HttpStatus.OK);

    }

    @GetMapping("/api/{name}/{elementID}")
    public ResponseEntity<Element> getCurrentChartData
            (
                    @PathVariable(value = "name") String name
                    , @PathVariable(value = "elementID") String elementID
                    , @RequestParam(value = "filter", required = false) Set<String> filter

            ) throws PropertiesLoadException, NoSuchElement {
        log.writeInfo("Trying to update chart with ID " + elementID + " on page " + name);

        List<String> filterList = new ArrayList<String>();

        if (filter != null) {
            filter.forEach((k) -> filterList.add(k));
            filterList.forEach(z -> System.out.println(z));

        }


        return new ResponseEntity<Element>(pageService.updateElement(elementID, pageService.get(name), filterList), HttpStatus.OK);

    }



}

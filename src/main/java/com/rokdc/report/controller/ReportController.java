package com.rokdc.report.controller;

import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.log.LogWriter;
import com.rokdc.report.model.App;
import com.rokdc.report.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
    private final String mainPage;

    private final PageService pageService;

    public ReportController(@Autowired PageService pageService
            , @Autowired LogWriter log
            , @Value("${settings.start-page}") String mainPage) {
        this.pageService = pageService;
        this.mainPage = mainPage;
    }

    @GetMapping(value = "/")
    public String index(Model model) throws PropertiesLoadException {
        App app = pageService.getApp();

        if (app != null) {
            return mainPage;

        } else
            throw new PropertiesLoadException(getClass() + ": fail to load index page. Add index page to your properties.json");
    }


    @GetMapping(value = "/{name}")
    public String index(Model model, @PathVariable("name") String name) throws PropertiesLoadException {
        App app = pageService.getApp();

        if (app != null) {
            return mainPage;

        } else
            throw new PropertiesLoadException(getClass() + ": fail to load index page. Add index page to your properties.json");
    }

    @GetMapping(value = "500")
    public ModelAndView _500(Model model) {
        return new ModelAndView("500", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

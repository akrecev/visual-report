package com.rokdc.report.controller;

import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.log.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @Autowired
    private LogWriter log;

    @ExceptionHandler(PropertiesLoadException.class)
    public ModelAndView handlePagePropertiesLoadException(HttpServletRequest request, Exception ex) {
        log.writeError("Requested URL=" + request.getRequestURL());
        log.writeError("Exception Raised=" + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());

        modelAndView.setViewName("500");

        return modelAndView;
    }
}

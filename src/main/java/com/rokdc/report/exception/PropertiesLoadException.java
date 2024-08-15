package com.rokdc.report.exception;

//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Dashboard properties.json deserialization fails")

public class PropertiesLoadException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5202172913945163647L;


    public PropertiesLoadException(String msg) {
        super(msg);
    }

}

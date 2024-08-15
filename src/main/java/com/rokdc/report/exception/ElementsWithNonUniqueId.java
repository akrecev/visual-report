package com.rokdc.report.exception;

public class ElementsWithNonUniqueId extends Exception {

    private final String message;

    public String getMessage() {
        return message;
    }

    private static final long serialVersionUID = 147788015396639845L;

    public ElementsWithNonUniqueId(String message) {
        super();
        this.message = message;
    }


}

package com.rokdc.report.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogWriter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void writeInfo(String message) {
        logger.info(message);
    }

    public void writeError(String message) {
        logger.error(message);
    }

    public void writeDebug(String message) {
        logger.debug(message);
    }

}

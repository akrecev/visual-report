package com.rokdc.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;


@SpringBootApplication
public class VisualReportApplication {
    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(VisualReportApplication.class, args);
    }
}
          
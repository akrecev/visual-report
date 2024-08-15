package com.rokdc.report.utils;

import com.rokdc.report.log.LogWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Query {
    private static final String SQL_FOLDER = "sql/";
    private final String sqlText;


    private final LogWriter log = new LogWriter();


    public Query(String fileName) throws FileNotFoundException {
        super();
        this.sqlText = readSqlFromFileToString(fileName);
    }


    private String readSqlFromFileToString(String fileName) throws FileNotFoundException {

        InputStream is = Properties.class.getClassLoader().getResourceAsStream(SQL_FOLDER + fileName);

        if (is == null) {
            throw new FileNotFoundException("Файл не найден");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));) {
            return reader.lines().collect(Collectors.joining("\n"));

        } catch (IOException e1) {
            log.writeError("Ошибка при работе с файлом " + fileName);
            return "";
        }

    }


    public String getSqlText() {
        return sqlText;
    }


}

package com.rokdc.report.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.log.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class Properties {

    private static LogWriter log;

    @Autowired
    private LogWriter logWriter;

    private static String path;

    private static String jsonText = null;

    private static Properties instance;

    private Properties(@Value("${settings.pages-properties}") String pathTofile) throws IOException {

        path = pathTofile;

    }

    @PostConstruct
    public void init() {
        log = logWriter;
        try (InputStream is = Properties.class.getClassLoader().getResourceAsStream(path);
             final Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);) {
            jsonText = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static <T extends Collection<?>> T deserialize(Class<? extends Collection<?>> collection, Class<?> clazz) throws PropertiesLoadException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CollectionType typeFacory = mapper.getTypeFactory()
                .constructCollectionType(collection, clazz);

        try {
            return mapper.readValue(jsonText, typeFacory);
        } catch (JsonMappingException e) {
            log.writeDebug(e.getPathReference() + "\n" + e.getMessage());
            throw new PropertiesLoadException(e.getMessage());

        } catch (JsonProcessingException e) {
            log.writeDebug(e.getMessage());
            throw new PropertiesLoadException(e.getMessage());
        }
    }

    public static <T> T deserialize(Class<T> clazz) throws PropertiesLoadException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonText, clazz);
        } catch (JsonMappingException e) {
            log.writeError(e.getPathReference() + "\n" + e.getMessage());
            throw new PropertiesLoadException(e.getMessage());

        } catch (JsonProcessingException e) {
            log.writeError(e.getMessage());
            throw new PropertiesLoadException(e.getMessage());
        }

    }


    public static Properties getInstance(String pathTofile) throws IOException {
        if (Properties.instance == null) {
            return instance = new Properties(pathTofile);
        }

        return instance;
    }


    public static String getPath() {
        log.writeInfo(path);
        return path;
    }

}

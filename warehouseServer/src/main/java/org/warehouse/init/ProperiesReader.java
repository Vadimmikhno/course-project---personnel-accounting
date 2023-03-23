package org.warehouse.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class ProperiesReader {


    private static Logger logger = LoggerFactory.getLogger(ProperiesReader.class);
    private static ProperiesReader properiesReader;
    private Properties properties;

    private static final String cfgFilePath = "src/main/resources/server.properties";

    public ProperiesReader() {
        properties = new Properties();
        read();
    }

    private void read() {
        try {
            FileInputStream fileInputStream = new FileInputStream(cfgFilePath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }

    private Properties getProperties() {
        return properties;
    }


    public static Optional<String> getPropery(String key) {
        if(properiesReader == null) {
            properiesReader = new ProperiesReader();
        }
        return Optional.of(properiesReader.getProperties().getProperty(key));
    }

    public static Integer getProperty(String key) {
        if(properiesReader == null) {
            properiesReader = new ProperiesReader();
        }
        String value = getPropery(key).get();
        return Integer.valueOf(value);
    }
}

package org.warehouse.init;

import org.apache.log4j.PropertyConfigurator;

import java.net.URL;

public class InitLog4J {
    private final InitLog4J initLogger;
    {
        initLogger = new InitLog4J();
    }
    static {
        new InitLog4J().init("log4j.properties");
    }
    public void init(String s) {
        URL url = getClass().getClassLoader().getResource(s);
        PropertyConfigurator.configure(url);
    }
}

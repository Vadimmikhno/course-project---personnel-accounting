package org.warehouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.init.ProperiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerFactory {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerFactory.class);
    private static Connection connection;


    private ConnectionManagerFactory() {}

    public static synchronized Connection connection() {
        if(connection != null) {
            return connection;
        }
        try {
            String url = ProperiesReader.getPropery("database.url").get();
            String username = ProperiesReader.getPropery("database.username").get();
            String password = ProperiesReader.getPropery("database.password").get();

            logger.info("Попытка соеденения с database url: {} username: {}", url, username);
            connection = DriverManager.getConnection(url,username,password);

        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new IllegalArgumentException("Ошибка подключения");
        }
        logger.info("Соеденение установлено");
        return connection;
    }
}

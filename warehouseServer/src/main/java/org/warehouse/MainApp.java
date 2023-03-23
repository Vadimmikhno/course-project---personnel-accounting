package org.warehouse;

import org.warehouse.init.ProperiesReader;
import org.warehouse.model.User;
import org.warehouse.repository.abstr.UserDao;
import org.warehouse.repository.impl.UserDaoImpl;
import org.warehouse.server.Server;

public class MainApp {

    public static void main(String[] args) {
        new Server().start();
    }
}

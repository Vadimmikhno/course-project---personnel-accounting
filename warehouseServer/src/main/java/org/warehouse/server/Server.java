package org.warehouse.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.init.ProperiesReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;
    private final Map<Long, ServerProcess> process = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private Long count = 1L;

    public void start() {
        try {
            this.serverSocket = new ServerSocket(ProperiesReader.getProperty("server.port"));
            logger.info("Сервер запущен на порту: {}", ProperiesReader.getProperty("server.port"));
            while (true) {
                Socket socket = serverSocket.accept();
                ServerProcess serverProcess = new ServerProcess(count, socket);
                new Thread(serverProcess).start();
                process.put(count, serverProcess);
                logger.info("Клиент с id: {} подключился", count);
                count++;
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

    }

    public void closeAll(){
        try {
            serverSocket.close();
            logger.info("Сервер остановлен");
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }


}

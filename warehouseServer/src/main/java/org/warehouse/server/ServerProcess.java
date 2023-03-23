package org.warehouse.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.exchange.ToClient;
import org.warehouse.router.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerProcess implements Runnable {
    private static Long id;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private static final Logger logger = LoggerFactory.getLogger(ServerProcess.class);
    private static final String warehouseKey = "warehouse";
    private static final String itemKey = "item";
    private static final String userKey = "user";
    private static final String esc = "esc";
    private static final String roleKey = "role";

    private AuthRouter authRouter;
    private WarehouseRouter warehouseRouter;
    private ItemRouter itemRouter;
    private UserRouter userRouter;
    private RoleRouter roleRouter;

    public ServerProcess(Long id, Socket socket) {
        this.id = id;
        this.socket = socket;
        try {
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        this.authRouter = new AuthRouter(this.in, this.out);
        this.warehouseRouter = new WarehouseRouter(this.in, this.out);
        this.itemRouter = new ItemRouter(this.in, this.out);
        this.userRouter = new UserRouter(this.in, this.out);
        this.roleRouter = new RoleRouter(this.in, this.out);
    }

    @Override
    public void run() {
        while (true) {
            String start = ToClient.accept(in);
            if(start.equals(esc)) {
                break;
            } else {
                authRouter.start(start);
            }

            while (true) {
                String key = ToClient.accept(in);

                if(key == null || key.equals("")) {
                    continue;
                }
                if(key.equals(warehouseKey)) {
                    warehouseRouter.start();
                }
                if(key.equals(itemKey)) {
                    itemRouter.start();
                }
                if(key.equals(userKey)) {
                    userRouter.start();
                }
                if(key.equals(roleKey)) {
                    roleRouter.start();
                }
                if(key.equals(esc)) {
                    break;
                }
            }
        }

        logger.info("Пользователь с id: {} отключился", id);
    }
}

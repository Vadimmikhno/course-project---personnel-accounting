package org.warehouse.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.exchange.ToClient;
import org.warehouse.model.User;
import org.warehouse.repository.impl.RoleDaoImpl;
import org.warehouse.repository.impl.UserDaoImpl;
import org.warehouse.server.ServerProcess;
import org.warehouse.service.abstr.UserService;
import org.warehouse.service.impl.RoleServiceImpl;
import org.warehouse.service.impl.UserServiceImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class AuthRouter {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthRouter.class);
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String authKey = "auth";

    public AuthRouter(InputStream inputStream, OutputStream outputStream) {
        this.userService = new UserServiceImpl(new UserDaoImpl());
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public User auth(String username, String password) {
        logger.info("Попытка авторизации from client username: {} password: {}", username, password);
        Optional<User> optionalUser = userService.findByUsername(username);
        if(optionalUser.isPresent()) {
            if(optionalUser.get().getPassword().equals(password)) {
                logger.info("User авторизован username: {} password: {}", username, password);
                return optionalUser.get();
            }
        }
        logger.info("User не существует username: {} password: {}", username, password);
        return null;
    }


    public void start(String startKey) {
        while (true) {


            if(startKey == null || startKey.equals("")) {
                continue;
            }

            if(startKey.equals(authKey)) {
                String userAndPassword = ToClient.accept(this.inputStream);
                String[] arr = userAndPassword.split(" ");
                if(arr.length == 2) {
                    User user = auth(arr[0], arr[1]);
                    if(user == null) {
                        ToClient.send(user, this.outputStream);
                        continue;
                    } else {
                        ToClient.send(user, this.outputStream);
                        break;
                    }

                }
            }
        }
    }
}

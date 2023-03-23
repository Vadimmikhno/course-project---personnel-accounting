package org.warehouse.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.exchange.ToClient;
import org.warehouse.model.User;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.impl.UserDaoImpl;
import org.warehouse.repository.impl.WarehouseDaoImpl;
import org.warehouse.service.abstr.UserService;
import org.warehouse.service.abstr.WarehouseService;
import org.warehouse.service.impl.UserServiceImpl;
import org.warehouse.service.impl.WarehouseServiceImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class UserRouter {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserRouter.class);
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String getByIdKey = "getById";
    private static final String escKey = "esc";
    private static final String deleteMappedItem = "deleteMappedItem";
    private static final String addMappedItem = "addMappedItem";
    private static final String getAll = "getAll";
    private static final String save = "save";
    private static final String update = "update";
    private static final String delete = "delete";

    public UserRouter(InputStream inputStream, OutputStream outputStream) {
        this.userService = new UserServiceImpl(new UserDaoImpl());
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void start() {
        while (true) {
            String key = ToClient.accept(this.inputStream);
            logger.info(key);
            if(key == null || key.equals("")) {
                continue;
            }

            /*if(key.equals(getByIdKey)) {
                Long id = ToClient.accept(this.inputStream);
                Optional<Warehouse> warehouse = warehouseService.findById(id);
                ToClient.send(warehouse.get(), this.outputStream);
                break;
            }
            if(key.equals(deleteMappedItem)) {
                Long warehouseId = ToClient.accept(this.inputStream);
                Long itemId = ToClient.accept(this.inputStream);
                warehouseService.deleteMappedItem(warehouseId, itemId);
                break;
            }
            if(key.equals(addMappedItem)) {
                Long warehouseId = ToClient.accept(this.inputStream);
                Long itemId = ToClient.accept(this.inputStream);
                warehouseService.addMappedItem(warehouseId, itemId);
                break;
            }*/
            if(key.equals(getAll)) {
                ToClient.send(userService.findAll().get(), this.outputStream);
                break;
            }
            if(key.equals(save)) {
                User user = ToClient.accept(this.inputStream);
                userService.save(user);
                break;
            }
            if(key.equals(update)) {
                User user = ToClient.accept(this.inputStream);
                userService.update(user);
                break;
            }
            if(key.equals(delete)) {
                Long id = ToClient.accept(this.inputStream);
                userService.deleteByKey(id);
                break;
            }
            if(key.equals(escKey)) {
                break;
            }
        }
    }
}

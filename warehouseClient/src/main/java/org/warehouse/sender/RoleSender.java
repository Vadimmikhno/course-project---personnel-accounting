package org.warehouse.sender;

import org.warehouse.client.Client;
import org.warehouse.client.MessageService;
import org.warehouse.model.Role;
import org.warehouse.model.Warehouse;

import java.util.List;

public class RoleSender {
    private Client client;
    private static final String getById = "getById";
    private static final String key = "role";
    private static final String deleteMappedItem = "deleteMappedItem";
    private static final String addMapperItem = "addMappedItem";
    private static final String getAll = "getAll";
    private static final String save = "save";
    private static final String esc = "esc";
    private static final String update = "update";
    private static final String delete = "delete";

    public RoleSender() {
        this.client = Client.getClient();
    }

    public Warehouse getById(Long id) {
        MessageService.send(key, client.getOut());
        MessageService.send(getById, client.getOut());
        MessageService.send(id, client.getOut());
        Warehouse warehouse = MessageService.accept(client.getInput());

        return warehouse;
    }

    public void addMapperItem(Long warehouseId, Long itemId) {
        MessageService.send(key, client.getOut());
        MessageService.send(addMapperItem, client.getOut());
        MessageService.send(warehouseId, client.getOut());
        MessageService.send(itemId, client.getOut());
    }

    public void deleteMappedItem(Long warehouseId, Long itemId) {
        MessageService.send(key, client.getOut());
        MessageService.send(deleteMappedItem, client.getOut());
        MessageService.send(warehouseId, client.getOut());
        MessageService.send(itemId, client.getOut());
    }
    public List<Role> getAll() {
        MessageService.send(key, client.getOut());
        MessageService.send(getAll, client.getOut());
        return MessageService.accept(client.getInput());
    }
    public void save(Warehouse warehouse) {
        MessageService.send(key, client.getOut());
        MessageService.send(save, client.getOut());
        MessageService.send(warehouse, client.getOut());
    }
    public void update(Warehouse warehouse) {
        MessageService.send(key, client.getOut());
        MessageService.send(update, client.getOut());
        MessageService.send(warehouse, client.getOut());
    }
    public void delete(Long id) {
        MessageService.send(key, client.getOut());
        MessageService.send(delete, client.getOut());
        MessageService.send(id, client.getOut());
    }
}

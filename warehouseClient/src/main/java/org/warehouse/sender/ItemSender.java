package org.warehouse.sender;

import org.warehouse.client.Client;
import org.warehouse.client.MessageService;
import org.warehouse.model.Item;
import org.warehouse.model.Warehouse;

import java.util.List;

public class ItemSender {
    private Client client;
    private static final String getAll = "getAll";
    private static final String key = "item";
    private static final String esc = "esc";

    public ItemSender() {
        this.client = Client.getClient();
    }

    public List<Item> getAll() {
        MessageService.send(key, client.getOut());
        MessageService.send(getAll, client.getOut());
        return MessageService.accept(client.getInput());

    }
}

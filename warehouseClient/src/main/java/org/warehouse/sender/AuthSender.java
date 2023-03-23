package org.warehouse.sender;

import org.warehouse.client.Client;
import org.warehouse.client.MessageService;
import org.warehouse.current.UserCurrent;
import org.warehouse.current.WareHouseCurrent;
import org.warehouse.model.User;

public class AuthSender {
    private Client client;
    private static final String authComand = "auth";
    private static final String esc = "esc";

    public AuthSender() {
        this.client = Client.getClient();
    }

    public boolean auth(String login, String password) {
        MessageService.send(authComand, client.getOut());
        MessageService.send(login + " " + password, client.getOut());
        User user = MessageService.accept(client.getInput());

        if(user == null) {
            return false;
        } else {
            UserCurrent.add(user);
            WareHouseCurrent.setWarehouse(user.getWarehouse());
            System.out.println(user);
            return true;
        }
    }
    public void esc() {
        MessageService.send(esc, client.getOut());
    }
}

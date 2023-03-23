package org.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.warehouse.current.UserCurrent;
import org.warehouse.current.WareHouseCurrent;
import org.warehouse.sender.AuthSender;
import org.warehouse.window.NewWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    private AuthSender authSender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.authSender = new AuthSender();

        loginButton.setOnAction(event -> {
            confirmLoginAndPassword();
            textFieldInitialize();
        });
    }

    private void confirmLoginAndPassword(){

        String login =  loginTextField.getText();
        String password = passwordField.getText();

        if(!login.equals("") && !password.equals("")) {

            boolean auth = authSender.auth(login, password);

            if(!auth) {
                NewWindow.alert("Не правильный логин или пароль!");
            } else {
                if(UserCurrent.getUserCurrent().getUser().getRole().getName().equals("ADMIN")) {
                    NewWindow.openWindow("/adminPanel.fxml");
                } else {
                    NewWindow.openWindow("/managerPanel.fxml");
                }
            }

        } else{
            NewWindow.alert("Заполните все ячейки !");
        }
    }

    private void textFieldInitialize(){
        loginTextField.setText("");
        passwordField.setText("");
    }
}

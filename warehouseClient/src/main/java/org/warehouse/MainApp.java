package org.warehouse;

import javafx.application.Application;
import javafx.stage.Stage;
import org.warehouse.client.Client;
import org.warehouse.window.NewWindow;

public class MainApp extends Application {


    public static void main(String[] args) {
        Client.start();
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        NewWindow.init(primaryStage);
        NewWindow.openWindow("/login.fxml");
    }
}

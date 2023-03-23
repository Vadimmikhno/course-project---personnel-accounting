package org.warehouse.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.warehouse.sender.AuthSender;

import java.io.IOException;

public class NewWindow {
    private static NewWindow newWindowOpen;
    private Stage primaryStage;
    private static AuthSender authSender = new AuthSender();

    private NewWindow(Stage primaryStage){
        this.primaryStage = primaryStage;
    }


    public static void init(Stage primaryStage) {
        newWindowOpen = new NewWindow(primaryStage);
        primaryStage.setOnCloseRequest(w -> esc());
    }
    public static void openWindow(String file)  {
        if(newWindowOpen != null) {
            Parent root = null;
            try {
                root = FXMLLoader.load(newWindowOpen.getClass().getResource(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            newWindowOpen.getPrimaryStage().setScene(new Scene(root));
            newWindowOpen.getPrimaryStage().show();
        }
    }
    public static void esc() {
        authSender.esc();
        authSender.esc();
        newWindowOpen.getPrimaryStage().close();
    }
    public static void alert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static NewWindow getNewWindow() {
        return newWindowOpen;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}

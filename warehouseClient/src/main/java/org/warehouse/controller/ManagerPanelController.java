package org.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.warehouse.current.UserCurrent;
import org.warehouse.current.WareHouseCurrent;
import org.warehouse.model.Category;
import org.warehouse.model.Item;
import org.warehouse.sender.AuthSender;
import org.warehouse.window.NewWindow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerPanelController implements Initializable {

    @FXML
    private Button escBtn;
    @FXML
    private Button warehouseBtn;
    @FXML
    private Text firstName;
    @FXML
    private Text lastName;
    @FXML
    private Text username;
    @FXML
    private Text warehouse;
    @FXML
    private Text role;
    @FXML
    private PieChart statistic;

    private UserCurrent userCurrent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userCurrent = UserCurrent.getUserCurrent();
        initText();
        initStatistic();

        warehouseBtn.setOnAction(event -> {
            NewWindow.openWindow("/warehousePanel.fxml");
        });
        escBtn.setOnAction(event -> {
            new AuthSender().esc();
            NewWindow.openWindow("/login.fxml");
        });

    }

    private void initStatistic() {
        List<Category> categories = WareHouseCurrent.getWarehouseCurrent().getWarehouse().getCategories();
        List<Item> items = WareHouseCurrent.getWarehouseCurrent().getWarehouse().getItems();

        for(Category c : categories) {
            Long count = items.stream()
                    .filter(i -> i.getCategory().getId() == c.getId())
                    .count();
            statistic.getData().add(new PieChart.Data(c.getName(), count));
        }
    }
    private void initText() {
        username.setText(String.format("Username: %s", userCurrent.getUser().getUsername()));
        firstName.setText(String.format("Имя: %s", userCurrent.getUser().getFirstName()));
        lastName.setText(String.format("Фамилия: %s", userCurrent.getUser().getLastName()));
        role.setText(String.format("Должность: %s", userCurrent.getUser().getRole().getName()));
        warehouse.setText(String.format("Склад: %s", userCurrent.getUser().getWarehouse().getName()));
    }



}

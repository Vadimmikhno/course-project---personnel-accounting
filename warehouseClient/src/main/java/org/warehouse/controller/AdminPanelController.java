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
import org.warehouse.model.Warehouse;
import org.warehouse.sender.AuthSender;
import org.warehouse.sender.WarehouseSender;
import org.warehouse.window.NewWindow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
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
    private Text role;
    @FXML
    private PieChart statistic;
    @FXML
    private Button itemBtn;
    @FXML
    private Button categoryBtn;
    @FXML
    private Button userBtn;

    private UserCurrent userCurrent;
    private WarehouseSender warehouseSender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.warehouseSender = new WarehouseSender();
        this.userCurrent = UserCurrent.getUserCurrent();
        initText();
        initStatistic();

        warehouseBtn.setOnAction(event -> {
            NewWindow.openWindow("/adminWarehousesPanel.fxml");
        });
        escBtn.setOnAction(event -> {
            new AuthSender().esc();
            NewWindow.openWindow("/login.fxml");
        });
        userBtn.setOnAction(event -> {
            NewWindow.openWindow("/adminUserPanel.fxml");
        });
    }

    private void initStatistic() {
        List<Warehouse> warehouses = warehouseSender.getAll();

        for(Warehouse w : warehouses) {
            Long count = w.getItems().stream().count();
            statistic.getData().add(new PieChart.Data(w.getName(), count));
        }
    }
    private void initText() {
        username.setText(String.format("Username: %s", userCurrent.getUser().getUsername()));
        firstName.setText(String.format("Имя: %s", userCurrent.getUser().getFirstName()));
        lastName.setText(String.format("Фамилия: %s", userCurrent.getUser().getLastName()));
        role.setText(String.format("Должность: %s", userCurrent.getUser().getRole().getName()));
    }
}

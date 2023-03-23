package org.warehouse.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.warehouse.current.WareHouseCurrent;
import org.warehouse.model.Category;
import org.warehouse.model.Item;
import org.warehouse.model.Warehouse;
import org.warehouse.sender.WarehouseSender;
import org.warehouse.window.NewWindow;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

public class AdminWarehousesPanelController implements Initializable {


    @FXML
    private Text numberItems;
    @FXML
    private Text squar;
    @FXML
    private Text numberEmployees;
    @FXML
    private Button escBtn;
    @FXML
    private TextField findByNameF;
    @FXML
    private Button findByNameBtn;
    @FXML
    private TableView<Warehouse> warehouseTable;
    @FXML
    private TableColumn<Item, Long> idCol;
    @FXML
    private TableColumn<Item, String> nameCol;
    @FXML
    private TableColumn<Item, Double> squarCol;
    @FXML
    private TableColumn<Item, String> employeesCol;
    @FXML
    private TextField addNameF;
    @FXML
    private TextField addSquarF;
    @FXML
    private TextField updateNameF;
    @FXML
    private TextField updateSquarF;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button goWarehouse;
    @FXML
    private Button deleteBtn;

    private WareHouseCurrent wareHouseCurrent;
    private WarehouseSender warehouseSender;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.wareHouseCurrent = WareHouseCurrent.getWarehouseCurrent();
        this.warehouseSender = new WarehouseSender();
        List<Warehouse> warehouses = warehouseSender.getAll();
        initText(warehouses);
        initTable(warehouses);

        warehouseTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null) {
                onSelectedRow(newValue);
            }
        }));
        goWarehouse.setOnAction(event -> {
            Warehouse warehouse = warehouseTable.getSelectionModel().getSelectedItem();
            if(warehouse == null) {
                NewWindow.alert("Выбреите склад");
                return;
            }
            wareHouseCurrent.setWarehouse(warehouse);
            NewWindow.openWindow("/warehousePanel.fxml");
        });

        findByNameBtn.setOnAction(event -> {
            String name = findByNameF.getText().trim();
            findByName(name);
        });

        addBtn.setOnAction(event -> {
            String name = addNameF.getText().trim();
            String squar = addSquarF.getText().trim();

            if(name == null || squar == null || name.equals("") || squar.equals("")) {
                NewWindow.alert("Заполните все поля");
                return;
            }
            try {
                Double sq = Double.parseDouble(squar);
                save(name, sq);
                initTable(warehouseSender.getAll());
            } catch (NumberFormatException e) {
                NewWindow.alert("Поле площади заполнено не корректно");
                return;
            }

        });

        updateBtn.setOnAction(event -> {
            Warehouse warehouse = warehouseTable.getSelectionModel().getSelectedItem();
            if(warehouse == null) {
                NewWindow.alert("Выберите элемент");
            }
            String name = updateNameF.getText();
            String squar = updateSquarF.getText();

            if(name == null || squar == null || name.equals("") || squar.equals("")) {
                NewWindow.alert("Заполните все поля");
                return;
            }
            try {
                Double sq = Double.parseDouble(squar);
                warehouse.setName(name);
                warehouse.setSquar(sq);

                update(warehouse);
                initTable(warehouseSender.getAll());
            } catch (NumberFormatException e) {
                NewWindow.alert("Поле площади заполнено не корректно");
                return;
            }
        });
        escBtn.setOnAction(event -> {
            NewWindow.openWindow("/adminPanel.fxml");
        });
        deleteBtn.setOnAction(event -> {
            Warehouse warehouse = warehouseTable.getSelectionModel().getSelectedItem();
            if(warehouse == null) {
                NewWindow.alert("Выберите элемент");
                return;
            }
            warehouseSender.delete(warehouse.getId());
            initTable(warehouseSender.getAll());
        });

    }

    public void update(Warehouse warehouse) {
        warehouseSender.update(warehouse);
    }

    public void save(String name, Double square) {
        warehouseSender.save(Warehouse.builder().name(name).squar(square).build());
    }
    public void findByName(String name) {
        List<Warehouse> items = warehouseSender.getAll();
        List<Warehouse> itemsByName = items.stream()
                .filter(w -> w.getName().indexOf(name) > -1)
                .collect(Collectors.toList());
        initTable(itemsByName);
    }

    public void onSelectedRow(Warehouse warehouse) {
        updateNameF.setText(warehouse.getName());
        updateSquarF.setText(warehouse.getSquar().toString());

    }

    public void initText(List<Warehouse> warehouses) {


        Long numberItems = warehouses.stream()
                .flatMap(w -> w.getItems().stream())
                .count();

        Double totalSquear = warehouses.stream()
                .flatMapToDouble(w -> DoubleStream.of(w.getSquar()))
                .sum();

        Long totalEmployees = warehouses.stream()
                .flatMapToLong(w -> LongStream.of(w.getNumberOfEmployees()))
                .sum();


        this.numberItems.setText(String.format("Кол-во товара: %s", numberItems));

        this.numberEmployees.setText(String.format("Кол-во сотрудников: %s",totalEmployees));

        this.squar.setText(String.format("Площадь: %s", totalSquear));
    }

    public void initTable(List<Warehouse> warehouses) {


        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        squarCol.setCellValueFactory(new PropertyValueFactory("squar"));
        employeesCol.setCellValueFactory(new PropertyValueFactory("numberOfEmployees"));

        warehouseTable.setItems(FXCollections.observableList(warehouses));
        warehouseTable.refresh();
    }
}

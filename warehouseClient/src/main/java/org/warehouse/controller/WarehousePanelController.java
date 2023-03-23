package org.warehouse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.warehouse.current.UserCurrent;
import org.warehouse.current.WareHouseCurrent;
import org.warehouse.model.Category;
import org.warehouse.model.Item;
import org.warehouse.model.Warehouse;
import org.warehouse.sender.ItemSender;
import org.warehouse.sender.WarehouseSender;
import org.warehouse.window.NewWindow;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WarehousePanelController implements Initializable {

    @FXML
    private Text name;
    @FXML
    private Text numberItems;
    @FXML
    private Text numberCategory;
    @FXML
    private Text totalPrice;
    @FXML
    private Text squar;
    @FXML
    private Text numberEmployees;
    @FXML
    private Button escBtn;
    @FXML
    private Button deleteItemBtn;
    @FXML
    private TextField findByNameF;
    @FXML
    private Button findByNameBtn;
    @FXML
    private ComboBox<Category> findByCategoryC;
    @FXML
    private Button findByCategoryBtn;
    @FXML
    private ComboBox<Item> addItemC;
    @FXML
    private Button addItemBtn;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableColumn<Item, Long> idCol;
    @FXML
    private TableColumn<Item, String> nameCol;
    @FXML
    private TableColumn<Item, Double> priceCol;
    @FXML
    private TableColumn<Item, String> categoryCol;
    @FXML
    private TableColumn<Item, Long> numberCol;

    private UserCurrent userCurrent;
    private WareHouseCurrent wareHouseCurrent;
    private WarehouseSender warehouseSender;
    private ItemSender itemSender;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userCurrent = UserCurrent.getUserCurrent();
        this.wareHouseCurrent = WareHouseCurrent.getWarehouseCurrent();
        this.warehouseSender = new WarehouseSender();
        this.itemSender = new ItemSender();

        wareHouseCurrent.update();
        initText();
        initTable(wareHouseCurrent.getWarehouse().getItems());
        initCombobox();

        escBtn.setOnAction(event -> {
            if(userCurrent.getUser().getRole().getName().equals("ADMIN")) {
                NewWindow.openWindow("/adminWarehousesPanel.fxml");
            } else {
                NewWindow.openWindow("/managerPanel.fxml");
            }
        });

        deleteItemBtn.setOnAction(event -> {
            Item item = itemsTable.getSelectionModel().getSelectedItem();
            if(item == null) {
                NewWindow.alert("Выберите элемент");
                return;
            }
            warehouseSender.deleteMappedItem(wareHouseCurrent.getWarehouse().getId(), item.getId());
            wareHouseCurrent.update();
            initTable(wareHouseCurrent.getWarehouse().getItems());
        });

        findByNameBtn.setOnAction(event -> {
            String name = findByNameF.getText().trim();
            findByName(name);
        });
        findByCategoryBtn.setOnAction(event -> {
            Category category = findByCategoryC.getSelectionModel().getSelectedItem();
            if(category != null) {
                findByCategory(category);
            }
        });
        addItemBtn.setOnAction(event -> {
            Item item = addItemC.getSelectionModel().getSelectedItem();
            if(item != null) {
                warehouseSender.addMapperItem(wareHouseCurrent.getWarehouse().getId(), item.getId());
                wareHouseCurrent.update();
                initTable(wareHouseCurrent.getWarehouse().getItems());
            }
        });
    }

    public void findByCategory(Category category) {
        List<Item> items = WareHouseCurrent.getWarehouseCurrent().getWarehouse().getItems();
        List<Item> itemsByCategory = items.stream()
                .filter(i -> i.getCategory().getName().equals(category.getName()))
                .collect(Collectors.toList());
        initTable(itemsByCategory);
    }
    public void initCombobox() {
        ObservableList<Category> categories = FXCollections.observableArrayList(wareHouseCurrent.getWarehouse().getCategories());
        List<Item> itemList = itemSender.getAll();
        ObservableList<Item> items = FXCollections.observableArrayList(itemList);
        this.findByCategoryC.getItems().setAll(categories);
        this.addItemC.getItems().setAll(items);
    }

    public void findByName(String name) {

        List<Item> items = WareHouseCurrent.getWarehouseCurrent().getWarehouse().getItems();
        List<Item> itemsByName = items.stream()
                .filter(i -> i.getName().indexOf(name) > -1)
                .collect(Collectors.toList());
        initTable(itemsByName);
    }
    public void initText() {
        Warehouse warehouse = wareHouseCurrent.getWarehouse();

        Long numberItems = warehouse.getItems().stream().count();
        Double totalPrice = warehouse.getItems().stream()
                .mapToDouble(i -> i.getPrice())
                .sum();
        Long numberCategory = warehouse.getCategories().stream().count();

        this.name.setText(String.format("Имя склада: %s", warehouse.getName()));
        this.numberItems.setText(String.format("Кол-во товара: %s", numberItems));
        this.numberCategory.setText(String.format("Кол-во категорий: %s", numberCategory));
        this.numberEmployees.setText(String.format("Кол-во сотрудников: %s",warehouse.getNumberOfEmployees().toString()));
        this.totalPrice.setText(String.format("Сумма денег: %s", totalPrice));
        this.squar.setText(String.format("Площадь: %s", warehouse.getSquar().toString()));
    }

    public void initTable(List<Item> items) {
        HashMap<Item, Long> dubl = new HashMap<>();
        for(Item i : items) {
            dubl.merge(i, 1l, (o,n) -> o + 1);
        }
        List<Item> notDublItems = dubl.entrySet().stream()
                        .map(e -> {
                            Item item = e.getKey();
                            item.setCount(e.getValue());
                            return item;
                        })
                        .collect(Collectors.toList());

        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        categoryCol.setCellValueFactory(new PropertyValueFactory("category"));
        numberCol.setCellValueFactory(new PropertyValueFactory("count"));
        itemsTable.setItems(FXCollections.observableList(notDublItems));
        itemsTable.refresh();
    }
}

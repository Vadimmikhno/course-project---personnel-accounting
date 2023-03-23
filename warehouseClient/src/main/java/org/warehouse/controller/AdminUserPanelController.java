package org.warehouse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.warehouse.model.*;
import org.warehouse.sender.RoleSender;
import org.warehouse.sender.UserSender;
import org.warehouse.sender.WarehouseSender;
import org.warehouse.window.NewWindow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminUserPanelController implements Initializable {


    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Long> idCol;
    @FXML
    private TableColumn<User, String> firstNameCol;
    @FXML
    private TableColumn<User, String> lastNameCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, String> roleCol;
    @FXML
    private TableColumn<User, String> warehouseCol;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField findNameF;
    @FXML
    private Button findNameBtn;
    @FXML
    private ComboBox<Warehouse> findWarehouseC;
    @FXML
    private Button findWarehouseBtn;
    @FXML
    private TextField updateFirstNameF;
    @FXML
    private TextField updateLastNameF;
    @FXML
    private TextField updateUsernameF;
    @FXML
    private TextField updatePasswordF;
    @FXML
    private ComboBox<Role> updateRoleC;
    @FXML
    private ComboBox<Warehouse> updateWarehouseC;
    @FXML
    private TextField saveFirstNameF;
    @FXML
    private TextField saveLastNameF;
    @FXML
    private TextField saveUsernameF;
    @FXML
    private TextField savePasswordF;
    @FXML
    private ComboBox<Role> saveRoleC;
    @FXML
    private ComboBox<Warehouse> saveWarehouseC;
    @FXML
    private Button updateBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button escBtn;

    private UserSender userSender;
    private WarehouseSender warehouseSender;
    private RoleSender roleSender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userSender = new UserSender();
        this.warehouseSender = new WarehouseSender();
        this.roleSender = new RoleSender();
        initTable(userSender.getAll());
        initRoleC(roleSender.getAll());
        initWarehouseC(warehouseSender.getAll());

        findNameBtn.setOnAction(event -> {
            String name = findNameF.getText().trim();
            findByName(name);
        });

        userTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null) {
                onSelectedRow(newValue);
            }
        }));
        updateBtn.setOnAction(event -> {
            User user = userTable.getSelectionModel().getSelectedItem();
            onUpdate(user);
            initTable(userSender.getAll());

        });
        saveBtn.setOnAction(event -> {
            onSave();
            initTable(userSender.getAll());
        });
        findWarehouseBtn.setOnAction(event -> {
            Warehouse warehouse = findWarehouseC.getSelectionModel().getSelectedItem();
            if(warehouse == null) {
                NewWindow.alert("Выберите склад");
                return;
            }
            findByWarehouse(warehouse);
        });
        deleteBtn.setOnAction(event -> {
            User user = userTable.getSelectionModel().getSelectedItem();
            onDelete(user);
            initTable(userSender.getAll());
        });
        escBtn.setOnAction(event -> {
            NewWindow.openWindow("/adminPanel.fxml");
        });

    }

    private void onDelete(User user) {
        if(user == null) {
            NewWindow.alert("Выберите пользователя");
            return;
        }
        userSender.delete(user.getId());
    }
    private void findByWarehouse(Warehouse warehouse) {
        List<User> users = userSender.getAll();
        List<User> userByWarehouse = users.stream()
                .filter(u -> u.getWarehouse().equals(warehouse))
                .collect(Collectors.toList());
        initTable(userByWarehouse);

    }

    private void onSave() {
        String firstName = saveFirstNameF.getText().trim();
        String lastName = saveLastNameF.getText().trim();
        String username = saveUsernameF.getText().trim();
        String password = savePasswordF.getText().trim();
        Role role = saveRoleC.getSelectionModel().getSelectedItem();
        Warehouse warehouse = saveWarehouseC.getSelectionModel().getSelectedItem();

        if(firstName == null || firstName.equals("") || lastName == null || lastName.equals("") ||
                username == null || username.equals("") || password == null || password.equals("")
                || role == null || warehouse == null) {
            NewWindow.alert("Заполните все поля");
            return;
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .role(role)
                .warehouse(warehouse)
                .build();

        userSender.save(user);
    }

    public void onUpdate(User user) {
        if(user == null) {
            NewWindow.alert("Выберите элемент");
            return;
        }
        String firstName = updateFirstNameF.getText().trim();
        String lastName = updateLastNameF.getText().trim();
        String username = updateUsernameF.getText().trim();
        String password = updatePasswordF.getText().trim();
        Role role = updateRoleC.getSelectionModel().getSelectedItem();
        Warehouse warehouse = updateWarehouseC.getSelectionModel().getSelectedItem();

        if(firstName == null || firstName.equals("") || lastName == null || lastName.equals("") ||
        username == null || username.equals("") || password == null || password.equals("")
        || role == null || warehouse == null) {
            NewWindow.alert("Заполните все поля");
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setWarehouse(warehouse);
        user.setRole(role);

        userSender.update(user);
    }

    public void onSelectedRow(User user) {
        if(user == null) {
            NewWindow.alert("Выберите элемент");
            return;
        }
        updateFirstNameF.setText(user.getFirstName());
        updateLastNameF.setText(user.getLastName());
        updateUsernameF.setText(user.getUsername());
        updatePasswordF.setText(user.getPassword());
        updateWarehouseC.getSelectionModel().select(user.getWarehouse());
        updateRoleC.getSelectionModel().select(user.getRole());
    }

    public void findByName(String name) {
        List<User> items = userSender.getAll();
        List<User> itemsByName = items.stream()
                .filter(w -> w.getFirstName().indexOf(name) > -1)
                .collect(Collectors.toList());
        initTable(itemsByName);
    }

    public void initWarehouseC(List<Warehouse> warehouses) {
        ObservableList<Warehouse> warehouseObservableList = FXCollections.observableArrayList(warehouses);
        this.findWarehouseC.getItems().setAll(warehouseObservableList);
        this.updateWarehouseC.getItems().setAll(warehouseObservableList);
        this.saveWarehouseC.getItems().setAll(warehouseObservableList);
    }

    public void initRoleC(List<Role> roles) {
        ObservableList<Role> roleObservableList = FXCollections.observableArrayList(roles);

        this.updateRoleC.getItems().setAll(roleObservableList);
        this.saveRoleC.getItems().setAll(roleObservableList);
    }

    public void initTable(List<User> users) {

        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory("password"));
        roleCol.setCellValueFactory(new PropertyValueFactory("role"));
        warehouseCol.setCellValueFactory(new PropertyValueFactory("warehouse"));
        userTable.setItems(FXCollections.observableList(users));
        userTable.refresh();
    }
}

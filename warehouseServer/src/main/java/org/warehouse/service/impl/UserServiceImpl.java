package org.warehouse.service.impl;

import org.warehouse.model.Role;
import org.warehouse.model.User;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.abstr.UserDao;
import org.warehouse.repository.impl.RoleDaoImpl;
import org.warehouse.repository.impl.WarehouseDaoImpl;
import org.warehouse.service.abstr.RoleService;
import org.warehouse.service.abstr.UserService;
import org.warehouse.service.abstr.WarehouseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {


    private UserDao userDao;
    private RoleService roleService;
    private WarehouseService warehouseService;

    public UserServiceImpl(UserDao userDao) {
        this.warehouseService = new WarehouseServiceImpl(new WarehouseDaoImpl());
        this.roleService = new RoleServiceImpl(new RoleDaoImpl());
        this.userDao = userDao;
    }


    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> findAll() {
        List<User> users = userDao.getAll();
        List<User> building = new ArrayList<>();

        for(User u : users) {
            building.add(build(u));
        }
        return Optional.ofNullable(building);
    }

    @Override
    public Optional<List<User>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean update(User o) {
        User user = userDao.findById(o.getId());
        Optional<Warehouse> warehouse = warehouseService.findById(user.getWarehouse().getId());

        if(warehouse.isPresent()) {
            if(!warehouse.get().equals(o.getWarehouse())) {
                Warehouse w = warehouse.get();
                w.setNumberOfEmployees(w.getNumberOfEmployees() - 1);
                warehouseService.update(w);
                o.getWarehouse().setNumberOfEmployees(o.getWarehouse().getNumberOfEmployees() + 1);
                warehouseService.update(o.getWarehouse());
            }
        }
        return userDao.update(o);
    }

    @Override
    public boolean deleteByKey(Long id) {

        User user = userDao.findById(id);

        if(user != null) {
            Optional<Warehouse> warehouse = warehouseService.findById(user.getWarehouse().getId());
            if(warehouse.isPresent()) {
                Warehouse w = warehouse.get();
                w.setNumberOfEmployees(w.getNumberOfEmployees() - 1);
                warehouseService.update(w);
            }
        }

        return userDao.deleteByKey(id);
    }

    @Override
    public boolean save(User o) {
        Optional<Warehouse> warehouse = warehouseService.findById(o.getWarehouse().getId());

        if(warehouse.isPresent()) {
            Warehouse w = warehouse.get();
            w.setNumberOfEmployees(w.getNumberOfEmployees() + 1);
            warehouseService.update(w);
        }
        return userDao.save(o);
    }

    public User build(User user) {
        if(user != null) {
            Optional<Role> role = roleService.findById(user.getRole().getId());
            Optional<Warehouse> warehouse = warehouseService.findById(user.getWarehouse().getId());

            if(role.isPresent()) user.setRole(role.get());
            if(warehouse.isPresent()) user.setWarehouse(warehouse.get());
        }
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = userDao.findByUsername(username);

        return Optional.ofNullable(build(user));
    }
}

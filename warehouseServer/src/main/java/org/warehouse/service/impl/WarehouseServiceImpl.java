package org.warehouse.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Category;
import org.warehouse.model.Item;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.abstr.WarehouseDao;
import org.warehouse.repository.impl.CategoryDaoImpl;
import org.warehouse.repository.impl.ItemDaoImpl;
import org.warehouse.service.abstr.CategoryService;
import org.warehouse.service.abstr.ItemService;
import org.warehouse.service.abstr.WarehouseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseServiceImpl implements WarehouseService {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
    private WarehouseDao warehouseDao;
    private CategoryService categoryService;
    private ItemService itemService;

    public WarehouseServiceImpl(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
        this.categoryService = new CategoryServiceImpl(new CategoryDaoImpl());
        this.itemService = new ItemServiceImpl(new ItemDaoImpl());
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        Warehouse warehouse = warehouseDao.findById(id);

        return Optional.ofNullable(build(warehouse));
    }

    private Warehouse build(Warehouse warehouse) {
        if(warehouse != null) {
            List<Category> categories = categoryService.findByWarehouseId(warehouse.getId());
            List<Item> items = itemService.findByWarehouseId(warehouse.getId());
            warehouse.setCategories(categories);
            warehouse.setItems(items);
        }
        return warehouse;
    }
    @Override
    public Optional<List<Warehouse>> findAll() {
        List<Warehouse> warehouses = warehouseDao.getAll();
        List<Warehouse> building = new ArrayList<>();
        for(Warehouse w : warehouses) {
            building.add(build(w));
        }
        return Optional.ofNullable(building);
    }

    @Override
    public Optional<List<Warehouse>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean update(Warehouse o) {
        return warehouseDao.update(o);
    }

    @Override
    public boolean deleteByKey(Long id) {
        return warehouseDao.deleteByKey(id);
    }

    @Override
    public boolean save(Warehouse o) {
        return warehouseDao.save(o);
    }

    @Override
    public void deleteMappedItem(Long warehouseId, Long itemId) {
        warehouseDao.deleteMappedItem(warehouseId, itemId);
    }

    @Override
    public void addMappedItem(Long warehouseId, Long itemId) {
        warehouseDao.addMappedItem(warehouseId, itemId);
    }
}

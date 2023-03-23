package org.warehouse.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Category;
import org.warehouse.model.Item;
import org.warehouse.repository.abstr.ItemDao;
import org.warehouse.repository.impl.CategoryDaoImpl;
import org.warehouse.service.abstr.CategoryService;
import org.warehouse.service.abstr.ItemService;

import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private ItemDao itemDao;
    private CategoryService categoryService;

    public ItemServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
        this.categoryService = new CategoryServiceImpl(new CategoryDaoImpl());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Item>> findAll() {
        List<Item> items = itemDao.getAll();
        if(items != null) {
            for(Item i : items) {
                buildForCategory(i);
            }
        }
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<Item>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean update(Item o) {
        return false;
    }

    @Override
    public boolean deleteByKey(Long id) {
        return false;
    }

    @Override
    public boolean save(Item o) {
        return false;
    }
    public Item buildForCategory(Item item) {
        item.setCategory(categoryService.findById(item.getCategory().getId()).get());
        return item;
    }
    @Override
    public List<Item> findByWarehouseId(Long id) {
        List<Item> items = itemDao.findByWarehouseId(id);
        if(items != null) {
            for(Item i : items) {
                buildForCategory(i);
            }
        }
        return items;
    }
}

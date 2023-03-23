package org.warehouse.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Category;
import org.warehouse.repository.abstr.CategoryDao;
import org.warehouse.service.abstr.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> findByWarehouseId(Long id) {
        return categoryDao.findByWarehouseId(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(categoryDao.findById(id));
    }

    @Override
    public Optional<List<Category>> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Category>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean update(Category o) {
        return false;
    }

    @Override
    public boolean deleteByKey(Long id) {
        return false;
    }

    @Override
    public boolean save(Category o) {
        return false;
    }
}

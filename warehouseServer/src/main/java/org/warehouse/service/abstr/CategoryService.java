package org.warehouse.service.abstr;

import org.warehouse.model.Category;

import java.util.List;

public interface CategoryService extends DefaultService<Category> {
    List<Category> findByWarehouseId(Long id);
}

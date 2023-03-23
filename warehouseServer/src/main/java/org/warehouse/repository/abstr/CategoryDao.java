package org.warehouse.repository.abstr;

import org.warehouse.model.Category;
import org.warehouse.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends DefaultDao<Category> {
    List<Category> findByWarehouseId(Long id);
}

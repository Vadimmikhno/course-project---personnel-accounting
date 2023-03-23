package org.warehouse.repository.abstr;

import org.warehouse.model.Category;
import org.warehouse.model.Item;

import java.util.List;

public interface ItemDao extends DefaultDao<Item> {
    List<Item> findByWarehouseId(Long id);

}

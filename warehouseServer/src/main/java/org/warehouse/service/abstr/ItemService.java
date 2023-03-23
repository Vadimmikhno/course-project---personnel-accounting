package org.warehouse.service.abstr;

import org.warehouse.model.Item;

import java.util.List;

public interface ItemService extends DefaultService<Item> {
    List<Item> findByWarehouseId(Long id);
}

package org.warehouse.repository.abstr;

import org.warehouse.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseDao extends DefaultDao<Warehouse> {
    void deleteMappedItem(Long warehouseId, Long itemId);
    void addMappedItem(Long warehouseId, Long itemId);
}

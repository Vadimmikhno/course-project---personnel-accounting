package org.warehouse.service.abstr;

import org.warehouse.model.Warehouse;

public interface WarehouseService extends DefaultService<Warehouse> {
    void deleteMappedItem(Long warehouseId, Long itemId);
    void addMappedItem(Long warehouseId, Long itemId);
}

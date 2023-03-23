package org.warehouse.current;

import org.warehouse.model.User;
import org.warehouse.model.Warehouse;
import org.warehouse.sender.WarehouseSender;

public class WareHouseCurrent {
    private Warehouse warehouse;
    private static WareHouseCurrent warehouseCurrent;
    private WarehouseSender warehouseSender;

    static {
        warehouseCurrent = new WareHouseCurrent();
    }

    private WareHouseCurrent() {
        this.warehouseSender = new WarehouseSender();
    }

    public static void update() {
        if(UserCurrent.getUserCurrent().getUser().getRole().getName().equals("MANAGER")) {
            Warehouse warehouse = warehouseCurrent.warehouseSender.getById(UserCurrent.getUserCurrent().getUser().getWarehouse().getId());
            if(warehouse != null) {
                warehouseCurrent.setWarehouse(warehouse);
            }
        } else {
            Warehouse warehouse = warehouseCurrent.warehouseSender.getById(warehouseCurrent.getWarehouse().getId());
            if(warehouse != null) {
                warehouseCurrent.setWarehouse(warehouse);
            }
        }
    }


    public static void setWarehouse(Warehouse warehouse) {
        warehouseCurrent.warehouse = warehouse;
    }
    public static WareHouseCurrent getWarehouseCurrent() {
        return warehouseCurrent;
    }

    public Warehouse getWarehouse() {
        return warehouseCurrent.warehouse;
    }
}

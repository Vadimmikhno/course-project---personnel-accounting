package org.warehouse.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.exchange.ToClient;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.impl.ItemDaoImpl;
import org.warehouse.repository.impl.WarehouseDaoImpl;
import org.warehouse.service.abstr.ItemService;
import org.warehouse.service.abstr.WarehouseService;
import org.warehouse.service.impl.ItemServiceImpl;
import org.warehouse.service.impl.WarehouseServiceImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class ItemRouter {
    private ItemService itemService;
    private static final Logger logger = LoggerFactory.getLogger(WarehouseRouter.class);
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String getByIdKey = "getById";
    private static final String escKey = "esc";
    private static final String getAll = "getAll";

    public ItemRouter(InputStream inputStream, OutputStream outputStream) {
        this.itemService = new ItemServiceImpl(new ItemDaoImpl());
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void start() {
        while (true) {
            String key = ToClient.accept(this.inputStream);
            logger.info(key);
            if(key == null || key.equals("")) {
                continue;
            }

            if(key.equals(getByIdKey)) {
                /*Long id = ToClient.accept(this.inputStream);
                Optional<Warehouse> warehouse = warehouseService.findById(id);
                ToClient.send(warehouse.get(), this.outputStream);*/
                break;
            }
            if(key.equals(getAll)) {
                ToClient.send(itemService.findAll().get(), this.outputStream);
                break;
            }
            if(key.equals(escKey)) {
                break;
            }
        }
    }
}

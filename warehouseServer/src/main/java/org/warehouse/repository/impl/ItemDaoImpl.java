package org.warehouse.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Category;
import org.warehouse.model.Item;
import org.warehouse.model.User;
import org.warehouse.repository.abstr.ItemDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl extends DefaultSql implements ItemDao {

    private static final String getByWarehouseId;
    private static final Logger logger = LoggerFactory.getLogger(ItemDaoImpl.class);

    static {
        getByWarehouseId = "select * from item as i join warehouse_item as wi on i.id = wi.item_id where wi.warehouse_id = ?";
    }

    public ItemDaoImpl() {
        super("item");
    }

    @Override
    public Item findById(Long id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        logger.info(super.findAll);

        try(Statement statement = super.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(super.findAll);
            List<Item> list = new ArrayList<>();

            while(resultSet.next()) {
                list.add(this.build(resultSet));
            }
            return list;

        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteByKey(Long id) {
        return false;
    }

    @Override
    public boolean update(Item o) {
        return false;
    }

    @Override
    public boolean save(Item o) {
        return false;
    }

    @Override
    public List<Item> findByWarehouseId(Long id) {
        logger.info(getByWarehouseId);

        try(PreparedStatement statement = super.connection.prepareStatement(getByWarehouseId)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Item> list = new ArrayList<>();

            while(resultSet.next()) {
                list.add(this.build(resultSet));
            }
            return list;

        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    private Item build(ResultSet resultSet) throws SQLException {
        return Item.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .price(resultSet.getDouble(3))
                .category(Category.builder().id(resultSet.getLong(4)).build())
                .build();
    }
}

package org.warehouse.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.User;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.abstr.WarehouseDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseDaoImpl extends DefaultSql implements WarehouseDao {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseDaoImpl.class);
    private static final String deleteMappedItem;
    private static final String addMapperItem;
    private static final String save;
    private static final String update;
    private static final String deleteMappedItems;
    private static final String deleteMappedCategoryes;

    static {
        deleteMappedItem = "delete from warehouse_item where warehouse_id = ? and item_id = ?";
        addMapperItem = "insert into warehouse_item (warehouse_id, item_id) values(?,?)";
        save = "insert into warehouse (name, squar) values(?,?)";
        update = "update warehouse set name = ?, squar = ?, number_of_employees = ? where id = ?";
        deleteMappedItems = "delete from warehouse_item where warehouse_id = ?";
        deleteMappedCategoryes = "delete from warehouse_category where warehouse_id = ?";

    }
    public WarehouseDaoImpl() {
        super("warehouse  ");
    }

    @Override
    public Warehouse findById(Long id) {
        logger.info(super.findById);

        try(PreparedStatement statement = super.connection.prepareStatement(super.findById)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return this.build(resultSet);
            } else {
                logger.info("Ошибка в запросе. Возможно warehouse с id={} не существует.", id);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;

    }

    private Warehouse build(ResultSet resultSet) throws SQLException {
        return Warehouse.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .squar(resultSet.getDouble(3))
                .numberOfEmployees(resultSet.getLong(4))
                .build();
    }

    @Override
    public List<Warehouse> getAll() {
        logger.info(super.findAll);

        try(Statement statement = super.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(super.findAll);
            List<Warehouse> list = new ArrayList<>();

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
        logger.info(super.deleteByKey);

        if(this.findById(id) == null) {
            return false;
        }
        try(PreparedStatement statement = connection.prepareStatement(super.deleteByKey);
        PreparedStatement statementForItem = connection.prepareStatement(deleteMappedItems);
        PreparedStatement statementForCategory = connection.prepareStatement(deleteMappedCategoryes)) {
            super.ofFk();
            statement.setLong(1, id);
            statement.executeUpdate();
            statementForCategory.setLong(1,id);
            statementForCategory.executeUpdate();
            statementForItem.setLong(1, id);
            statementForItem.executeUpdate();
            super.onFk();
            return true;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Warehouse o) {
        logger.info(update);

        try(PreparedStatement statement = super.connection.prepareStatement(update)) {
            statement.setString(1, o.getName());
            statement.setDouble(2, o.getSquar());
            statement.setLong(3, o.getNumberOfEmployees());
            statement.setLong(4, o.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean save(Warehouse o) {
        logger.info(save);

        try(PreparedStatement statement = super.connection.prepareStatement(save)) {
            statement.setString(1, o.getName());
            statement.setDouble(2, o.getSquar());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            return false;
        }
    }


    @Override
    public void deleteMappedItem(Long warehouseId, Long itemId) {
        logger.info(deleteMappedItem);

        try(PreparedStatement statement = super.connection.prepareStatement(deleteMappedItem)) {
            statement.setLong(1,warehouseId);
            statement.setLong(2, itemId);
            statement.executeUpdate();

        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    @Override
    public void addMappedItem(Long warehouseId, Long itemId) {
        logger.info(addMapperItem);

        try(PreparedStatement statement = super.connection.prepareStatement(addMapperItem)) {
            statement.setLong(1,warehouseId);
            statement.setLong(2, itemId);
            statement.executeUpdate();

        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }
}

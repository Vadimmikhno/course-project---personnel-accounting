package org.warehouse.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Category;
import org.warehouse.model.User;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.abstr.CategoryDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl extends DefaultSql implements CategoryDao {

    private static final String getByWarehouseId;
    private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

    static {
        getByWarehouseId = "select * from category as c join warehouse_category as wc on c.id = wc.category_id where wc.warehouse_id = ?";
    }

    public CategoryDaoImpl() {
        super("category");
    }

    @Override
    public List<Category> findByWarehouseId(Long id) {
        logger.info(getByWarehouseId);

        try(PreparedStatement statement = super.connection.prepareStatement(getByWarehouseId)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Category> list = new ArrayList<>();

            while(resultSet.next()) {
                list.add(this.build(resultSet));
            }
            return list;

        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    private Category build(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .build();
    }

    @Override
    public Category findById(Long id) {
        logger.info(super.findById);
        ResultSet resultSet = null;
        try(PreparedStatement statement = super.connection.prepareStatement(super.findById)) {
            statement.setLong(1,id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return this.build(resultSet);
            } else {
                logger.info("Ошибка в запросе. Возможно category с id={} не существует.", id);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public boolean deleteByKey(Long id) {
        return false;
    }

    @Override
    public boolean update(Category o) {
        return false;
    }

    @Override
    public boolean save(Category o) {
        return false;
    }
}

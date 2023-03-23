package org.warehouse.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Role;
import org.warehouse.model.User;
import org.warehouse.repository.abstr.RoleDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl extends DefaultSql implements RoleDao {

    private final static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    private final static String findByName;


    static {
        findByName = "select * from role where name = ?";
    }

    public RoleDaoImpl() {
        super("role");
    }

    @Override
    public Role findById(Long id) {
        logger.info(super.findById);

        try(PreparedStatement statement = super.connection.prepareStatement(super.findById)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return this.build(resultSet);
            } else {
                logger.info("Ошибка в запросе. Возможно role с id={} не существует.", id);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;

    }

    @Override
    public List<Role> getAll() {
        logger.info(super.findAll);

        try(Statement statement = super.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(super.findAll);
            List<Role> list = new ArrayList<>();

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
    public boolean update(Role o) {
        return false;
    }

    @Override
    public boolean save(Role o) {
        return false;
    }

    @Override
    public Role findByName(String name) {
        logger.info(findByName);


        try(PreparedStatement statement = super.connection.prepareStatement(findByName)) {
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return this.build(resultSet);
            } else {
                logger.info("Ошибка в запросе. Возможно role с name={} не существует.", name);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    private Role build(ResultSet resultSet) throws SQLException {
        return  Role.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .build();
    }


}

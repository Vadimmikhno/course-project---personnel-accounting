package org.warehouse.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Role;
import org.warehouse.model.User;
import org.warehouse.model.Warehouse;
import org.warehouse.repository.abstr.UserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends DefaultSql implements UserDao {

    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String update;
    private static final String save;
    private static final String findByFirstName;
    private static final String findByUsername;

    static {
        update = "update user set first_name = ?, last_name = ?, username = ?, password = ?, role_id = ?, warehouse_id = ? where id = ?";
        save = "insert into user (first_name, last_name, username, password, role_id, warehouse_id) values(?,?,?,?,?,?)";
        findByFirstName = "select * from user where first_name like ?";
        findByUsername = "select * from user where username = ?";
    }

    public UserDaoImpl() {
        super("user");
    }

    private User build(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(1))
                .firstName(resultSet.getString(4))
                .lastName(resultSet.getString(5))
                .username(resultSet.getString(2))
                .password(resultSet.getString(3))
                .role(Role.builder().id(resultSet.getLong(6)).build())
                .warehouse(Warehouse.builder().id(resultSet.getLong(7)).build())
                .build();
    }

    @Override
    public User findById(Long id) {
        logger.info(super.findById);
        ResultSet resultSet = null;
        try(PreparedStatement statement = super.connection.prepareStatement(super.findById)) {
            statement.setLong(1,id);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return this.build(resultSet);
            } else {
                logger.info("Ошибка в запросе. Возможно user с id={} не существует.", id);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {

        logger.info(super.findAll);

        try(Statement statement = super.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(super.findAll);
            List<User> list = new ArrayList<>();

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
        try(PreparedStatement statement = connection.prepareStatement(super.deleteByKey)) {
            super.ofFk();
            statement.setLong(1, id);
            statement.executeUpdate();
            super.onFk();
            return true;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(User o) {
        logger.info(update);

        try(PreparedStatement statement = super.connection.prepareStatement(update)) {
            statement.setString(1, o.getFirstName());
            statement.setString(2, o.getLastName());

            statement.setString(3, o.getUsername());
            statement.setString(4, o.getPassword());
            statement.setLong(5, o.getRole().getId());
            statement.setLong(6, o.getWarehouse().getId());
            statement.setLong(7, o.getId());

            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(User o) {
        logger.info(save);

        try(PreparedStatement statement = super.connection.prepareStatement(save)) {
            statement.setString(1, o.getFirstName());
            statement.setString(2, o.getLastName());
            statement.setString(3, o.getUsername());
            statement.setString(4, o.getPassword());
            statement.setLong(5, o.getRole().getId());
            statement.setLong(6, o.getWarehouse().getId());

            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            logger.info("Возможно такой email или username же существует");
            logger.warn(ex.getMessage());
            return false;
        }
    }

    @Override
    public User findByUsername(String name) {
        logger.info(findByUsername);
        ResultSet resultSet = null;
        try(PreparedStatement statement = super.connection.prepareStatement(findByUsername)) {
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return this.build(resultSet);
            } else {
                logger.info("Ошибка в запросе. Возможно user с username={} не существует.", name);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getByFirstName(String name) {
        logger.info(findByFirstName);

        try(PreparedStatement statement = super.connection.prepareStatement(findByFirstName)) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();

            List<User> list = new ArrayList<>();

            while(resultSet.next()) {
                list.add(this.build(resultSet));
            }
            return list;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            return null;
        }
    }
}

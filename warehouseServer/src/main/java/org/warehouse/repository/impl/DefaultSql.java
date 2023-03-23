package org.warehouse.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.util.ConnectionManagerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DefaultSql {
    protected String findById;
    protected String findAll;
    protected String deleteByKey;
    protected Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(DefaultSql.class);

    protected String ofFK;
    protected String onFk;
    protected String currentId;


    protected DefaultSql(String tableName) {
        this.connection = ConnectionManagerFactory.connection();
        findById = "select * from %s where id = ?";
        findAll = "select * from %s";
        deleteByKey = "delete from %s where id = ?";
        ofFK = "SET FOREIGN_KEY_CHECKS=0";
        onFk = "SET FOREIGN_KEY_CHECKS=1";
        currentId = "select max(id) from %s";
        init(tableName);
    }

    protected void setTableName(String tableName) {
        this.init(tableName);
    }

    public void ofFk() {
        try (Statement offFk = connection.createStatement()) {
            offFk.executeQuery(this.ofFK);
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }
    public void onFk() {
        try (Statement onFk = connection.createStatement()) {
            onFk.executeQuery(this.onFk);
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }
    private void init(String tableName) {
        this.findById = String.format(findById, tableName);
        this.findAll = String.format(findAll, tableName);
        this.deleteByKey = String.format(deleteByKey, tableName);
        this.currentId = String.format(currentId, tableName);
    }
}

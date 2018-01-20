package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.TransactionManager;
import by.epam.tc.conference.dao.mysql.pool.ConnectionPoolException;
import by.epam.tc.conference.dao.mysql.pool.Connector;

import java.sql.SQLException;

public class TransactionManagerImpl implements TransactionManager {

    private Connector connector;

    public TransactionManagerImpl(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void startTransaction() throws DaoException {
        try {
            connector.startTransaction();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Failed to start transaction: " + e.getMessage(), e);
        }
    }

    @Override
    public void commit() throws DaoException {
        try {
            connector.commitTransaction();
        } catch (SQLException e) {
            throw new DaoException("Failed to commit transaction: " + e.getMessage(), e);
        }
    }

    public void rollback() throws DaoException {
        try {
            connector.rollback();
        } catch (SQLException e) {
            throw new DaoException("Failed to rollback transaction: " + e.getMessage(), e);
        }
    }
}

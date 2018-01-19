package by.epam.tc.conference.dao.mysql.connectionpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectorImpl implements Connector {

    private final ConnectionPool connectionPool;
    private Connection connection;
    private int transactionStackSize;

    public ConnectorImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException,
            ConnectionPoolException {
        if (connection == null) {
            connection = connectionPool.takeConnection();
        }
        return connection.prepareStatement(sql, autoGeneratedKeys);
    }

    public void startTransaction() throws SQLException, ConnectionPoolException {
        if (connection == null) {
            connection = connectionPool.takeConnection();
        }
        connection.setAutoCommit(true);
        transactionStackSize++;
    }

    public void commitTransaction() throws SQLException {
        decrementTransactionCount();
        if (transactionStackSize == 0) {
            connection.commit();
            connection.setAutoCommit(false);
        }
    }

    public void rollback() throws SQLException {
        transactionStackSize = 0;
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public void closeConnection() {
        connectionPool.putConnection(connection);
        connection = null;
    }

    private void decrementTransactionCount() {
        if (--transactionStackSize < 0) {
            throw new IllegalStateException("Transaction count can't be negative");
        }
    }
}
package by.epam.tc.conference.dao.connectionpool;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> givenAwayConnections;

    public ConnectionPool() {
        initPool();
    }

    private void initPool() {
        DBResourceManager resourceManager = DBResourceManager.getInstance();
        MysqlDataSource dataSource = new MysqlDataSource();
        String url = resourceManager.getValue(DBParameter.DB_URL);
        dataSource.setURL(url);
        String user = resourceManager.getValue(DBParameter.USER);
        dataSource.setUser(user);
        String password = resourceManager.getValue(DBParameter.PASSWORD);
        dataSource.setPassword(password);
        String poolSize = resourceManager.getValue(DBParameter.POOL_SIZE);
        Integer capacity = Integer.valueOf(poolSize);
        availableConnections = new ArrayBlockingQueue<Connection>(capacity);
        for (int i = 0; i < capacity; i++) {
            try {
                availableConnections.add(dataSource.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        givenAwayConnections = new ArrayBlockingQueue<Connection>(Integer.valueOf(poolSize));
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = availableConnections.take();
            givenAwayConnections.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public void closeConnection(Connection connection) throws ConnectionPoolException {
        if (givenAwayConnections.remove(connection)) {
            availableConnections.add(connection);
        } else {
            throw new ConnectionPoolException("sda");
        }
    }
}

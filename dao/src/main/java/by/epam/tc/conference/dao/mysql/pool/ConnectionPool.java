package by.epam.tc.conference.dao.mysql.pool;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final String VALIDATION_QUERY = "/* ping */ SELECT 1";
    private static final ReentrantLock lock = new ReentrantLock();
    private volatile static ConnectionPool INSTANCE;

    private final MysqlDataSource dataSource = new MysqlDataSource();
    private final ThreadLocal<Connection> localConnection = new ThreadLocal<>();
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> givenAwayConnections;

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        if (INSTANCE == null) {
            try {
                lock.lock();
                if (INSTANCE == null) {
                    ConnectionPool pool = new ConnectionPool();
                    pool.init();
                    INSTANCE = pool;
                }
            } finally {
                lock.unlock();
            }
        }
        return INSTANCE;
    }

    private void init() {
        DBResourceManager resourceManager = DBResourceManager.getInstance();
        String url = resourceManager.getValue(DBParameter.DB_URL);
        dataSource.setURL(url);
        String user = resourceManager.getValue(DBParameter.USER);
        dataSource.setUser(user);
        String password = resourceManager.getValue(DBParameter.PASSWORD);
        dataSource.setPassword(password);
        String poolSize = resourceManager.getValue(DBParameter.POOL_SIZE);
        Integer capacity = Integer.valueOf(poolSize);
        givenAwayConnections = new ArrayBlockingQueue<>(capacity);
        availableConnections = new ArrayBlockingQueue<>(capacity);
        for (int i = 0; i < capacity; i++) {
            try {
                availableConnections.add(createConnection());
            } catch (ConnectionPoolException e) {
                throw new ConnectionPoolInitializationException("Error during initialization " + e.getMessage());
            }
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = localConnection.get();
        if (connection == null) {
            connection = takeFromAvailable();
            localConnection.set(connection);
        }

        if (validateConnection(connection)) {
            return connection;
        }

        Connection newConnection = createConnection();
        replaceGivenAwayConnection(connection, newConnection);
        localConnection.set(connection);
        return newConnection;
    }

    public void putConnection(Connection connection) {
        boolean removed = givenAwayConnections.remove(connection);
        if (removed) {
            localConnection.remove();
            availableConnections.add(connection);
        }
    }

    private Connection createConnection() throws ConnectionPoolException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionPoolException("Failed to create connection to DB: " + e.getMessage(), e);
        }
    }

    private boolean validateConnection(Connection connection) {
        boolean valid = true;
        try {
            Statement statement = connection.createStatement();
            statement.execute(VALIDATION_QUERY);
        } catch (SQLException e) {
            valid = false;
        }
        return valid;
    }

    private void replaceGivenAwayConnection(Connection oldConnection, Connection newConnection) {
        givenAwayConnections.remove(oldConnection);
        givenAwayConnections.add(newConnection);
    }

    private Connection takeFromAvailable() throws ConnectionPoolException {
        try {
            Connection connection = availableConnections.take();
            givenAwayConnections.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Failed to take available connection: " + e.getMessage(), e);
        }
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignored
            }
        }
    }
}

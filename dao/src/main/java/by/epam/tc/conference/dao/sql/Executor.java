package by.epam.tc.conference.dao.sql;

import by.epam.tc.conference.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor<K, V> {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void executeUpdate(StatementConfigurator<V> configurator, String query, V entity) throws
            DaoException {
        try (PreparedStatement statement = createStatement(query)) {
            configurator.configure(entity, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public <T> T execute(StatementConfigurator<K> configurator, String query, Handler<T> resultHandler, K id)
            throws
            DaoException {
        try (PreparedStatement statement = createStatement(query)) {
            configurator.configure(id, statement);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            return resultHandler.handle(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private PreparedStatement createStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}

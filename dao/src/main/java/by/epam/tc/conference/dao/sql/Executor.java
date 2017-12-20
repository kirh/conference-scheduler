package by.epam.tc.conference.dao.sql;

import by.epam.tc.conference.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor<T> {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void executeUpdate(StatementConfigurator<T> configurator, String query, T entity) throws
            DaoException {
        try (PreparedStatement statement = createStatement(query)) {
            configurator.configure(entity, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public <R> R execute(StatementConfigurator<Long> configurator, String query, Handler<R> resultHandler, Long id)
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

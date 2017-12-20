package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Identifiable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractDao<K, V extends Identifiable<K>> implements GenericDao<K, V> {

    private final Connection connection;
    private final ResultHandler<V> resultHandler;

    public AbstractDao(Connection connection, ResultHandler<V> resultHandler) {
        this.connection = connection;
        this.resultHandler = resultHandler;
    }

    @Override
    public void save(V entity) throws DaoException {
        executeUpdate(this::configureSaveStatement, getSaveQuery());
    }

    @Override
    public void update(V entity) throws DaoException {
        executeUpdate(this::configureUpdateStatement, getUpdateQuery());
    }

    @Override
    public void delete(V entity) throws DaoException {
        executeUpdate(this::configureDeleteStatement, getDeleteQuery());
    }

    @Override
    public List<V> findAll() throws DaoException {
        return execute((dummy) -> { }, getSelectAllQuery(), resultHandler::fetchAll);
    }

    @Override
    public Optional<V> findById(K id) throws DaoException {
        return execute(this::configureSelectByIdStatement, getSelectByIdQuery(), resultHandler::fetchOne);
    }

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSaveQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract void configureUpdateStatement(PreparedStatement statement);

    protected abstract void configureSelectByIdStatement(PreparedStatement statement);

    protected abstract void configureSaveStatement(PreparedStatement statement);

    protected abstract void configureDeleteStatement(PreparedStatement statement);

    private void executeUpdate(Consumer<PreparedStatement> configurator, String query) throws DaoException {
        try (PreparedStatement statement = createStatement(query)) {
            configurator.accept(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private <T> T execute(Consumer<PreparedStatement> configurator, String query, Handler<T> handler) throws
            DaoException {
        try(PreparedStatement statement = createStatement(query)) {
            configurator.accept(statement);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private PreparedStatement createStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}

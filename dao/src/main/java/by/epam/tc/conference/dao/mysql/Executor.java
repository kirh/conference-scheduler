package by.epam.tc.conference.dao.mysql;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.mysql.connector.Connector;
import by.epam.tc.conference.dao.mysql.pool.ConnectionPoolException;
import by.epam.tc.conference.dao.mysql.rowmapper.RowMapper;
import by.epam.tc.conference.entity.Identifiable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Executes query with given parameters and maps result to T.
 *
 * @param <T> the type to map result
 */
public class Executor<T extends Identifiable> {

    private final Connector connector;
    private final RowMapper<T> rowMapper;

    public Executor(Connector connector, RowMapper<T> rowMapper) {
        this.connector = connector;
        this.rowMapper = rowMapper;
    }

    /**
     * Executes update query based on input sql query and params.
     *
     * @param query       sql query to execute
     * @param queryParams params to insert into query
     * @throws DaoException when error during db request occurs
     */
    public void executeUpdate(String query, Object... queryParams) throws
            DaoException {
        try (PreparedStatement statement = createStatement(query, queryParams, Statement.NO_GENERATED_KEYS)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to execute update query: " + e.getMessage(), e);
        } finally {
            connector.closeConnection();
        }
    }

    /**
     * Executes update statement and returns generated id.
     *
     * @param query       sql to execute
     * @param queryParams parameters to fill in sql query.
     * @return generated id
     * @throws DaoException when no generated key found or another error during data access
     */
    public long executeInsert(String query, Object... queryParams) throws DaoException {
        try (PreparedStatement statement = createStatement(query, queryParams, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            return getGeneratedId(statement);
        } catch (SQLException e) {
            throw new DaoException("Failed to execute update query and get id " + e.getMessage(), e);
        } finally {
            connector.closeConnection();
        }
    }

    /**
     * Executes query and attempts to fetch one row. Returns optional object of T when result contains
     * row or empty optional otherwise.
     *
     * @param query       sql to execute
     * @param queryParams parameters to fill in sql query
     * @return optional object of T when result contains row or empty optional otherwise.
     * @throws DaoException when error during data  access occurs
     */
    public Optional<T> executeAndFetchOne(String query, Object... queryParams) throws DaoException {
        try (PreparedStatement statement = createStatement(query, queryParams, Statement.NO_GENERATED_KEYS);
             ResultSet resultSet = executePrepared(statement)) {
            if (resultSet.next()) {
                T object = rowMapper.map(resultSet);
                return Optional.of(object);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("Failed to execute query " + e.getMessage(), e);
        } finally {
            connector.closeConnection();
        }
    }

    /**
     * Executes query and attempts to map fetch all rows.
     * @param query sql to execute
     * @param queryParams parameters to fill in sql query
     * @return list of T objects returned by query
     * @throws DaoException when error during data  access occurs
     */
    public List<T> executeAndFetchAll(String query, Object... queryParams) throws DaoException {
        try (PreparedStatement statement = createStatement(query, queryParams, Statement.NO_GENERATED_KEYS);
             ResultSet resultSet = executePrepared(statement)) {
            List<T> objects = new ArrayList<>();
            while (resultSet.next()) {
                T object = rowMapper.map(resultSet);
                objects.add(object);
            }
            return objects;
        } catch (SQLException e) {
            throw new DaoException("Failed to execute query " + e.getMessage(), e);
        } finally {
            connector.closeConnection();
        }
    }

    private long getGeneratedId(PreparedStatement statement) throws SQLException {
        try (ResultSet keysResultSet = statement.getGeneratedKeys()) {
            if (keysResultSet.next()) {
                int id = keysResultSet.getInt(1);
                return Integer.toUnsignedLong(id);
            }
            throw new SQLException("No generated key found");
        }
    }

    private ResultSet executePrepared(PreparedStatement statement) throws SQLException {
        statement.execute();
        return statement.getResultSet();
    }

    private PreparedStatement createStatement(String query, Object[] queryParams, int autoGeneratedKeys) throws
            SQLException,
            ConnectionPoolException {
        PreparedStatement prepareStatement = connector.prepareStatement(query, autoGeneratedKeys);
        for (int i = 0; i < queryParams.length; i++) {
            int parameterIndex = i + 1;
            prepareStatement.setObject(parameterIndex, queryParams[i]);
        }
        return prepareStatement;
    }
}

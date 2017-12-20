package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.GenericDao;
import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.entity.Identifiable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<K, V extends Identifiable<K>> implements GenericDao<K, V> {

    private final ResultHandler<V> resultHandler;
    private final Executor<K, V> executor;

    public AbstractDao(ResultHandler<V> resultHandler, Executor<K, V> executor) {

        this.resultHandler = resultHandler;
        this.executor = executor;
    }

    @Override
    public void save(V entity) throws DaoException {
        executor.executeUpdate(this::configureSaveStatement, getSaveQuery(), entity);
    }

    @Override
    public void update(V entity) throws DaoException {
        executor.executeUpdate(this::configureUpdateStatement, getUpdateQuery(), entity);
    }

    @Override
    public void delete(V entity) throws DaoException {
        executor.executeUpdate(this::configureDeleteStatement, getDeleteQuery(), entity);
    }

    @Override
    public List<V> findAll() throws DaoException {
        return executor.execute((dummy, id) -> {
        }, getSelectAllQuery(), resultHandler::fetchAll, null);
    }

    @Override
    public Optional<V> findById(K id) throws DaoException {
        return executor.execute(this::configureSelectByIdStatement, getSelectByIdQuery(), resultHandler::fetchOne, id);
    }

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSaveQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract void configureUpdateStatement(V entity, PreparedStatement statement) throws SQLException;

    protected abstract void configureSelectByIdStatement(K id, PreparedStatement statement) throws SQLException;

    protected abstract void configureSaveStatement(V entity, PreparedStatement statement) throws SQLException;

    protected abstract void configureDeleteStatement(V entity, PreparedStatement statement) throws SQLException;

}

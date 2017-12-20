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

public abstract class AbstractDao<T extends Identifiable> implements GenericDao<T> {

    protected final ResultHandler<T> resultHandler;
    protected final Executor<T> executor;

    public AbstractDao(ResultHandler<T> resultHandler, Executor<T> executor) {

        this.resultHandler = resultHandler;
        this.executor = executor;
    }

    @Override
    public void save(T entity) throws DaoException {
        executor.executeUpdate(this::configureSaveStatement, getSaveQuery(), entity);
    }

    @Override
    public void update(T entity) throws DaoException {
        executor.executeUpdate(this::configureUpdateStatement, getUpdateQuery(), entity);
    }

    @Override
    public void delete(T entity) throws DaoException {
        executor.executeUpdate(this::configureStatementWithId, getDeleteQuery(), entity);
    }

    @Override
    public List<T> findAll() throws DaoException {
        return executor.execute((dummy, id) -> {
        }, getSelectAllQuery(), resultHandler::fetchAll, null);
    }

    @Override
    public Optional<T> findById(Long id) throws DaoException {
        return executor.execute(this::configureStatementWithId, getSelectByIdQuery(), resultHandler::fetchOne, id);
    }

    protected void configureStatementWithId(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    protected void configureStatementWithId(T entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());
    }

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSaveQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract void configureUpdateStatement(T entity, PreparedStatement statement) throws SQLException;

    protected abstract void configureSaveStatement(T entity, PreparedStatement statement) throws SQLException;

}

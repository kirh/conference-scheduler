package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.GenericDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements GenericDao<T> {

    protected final Executor<T> executor;

    protected AbstractDao(Executor<T> executor) {
        this.executor = executor;
    }

    @Override
    public void save(T entity) throws DaoException {
        Object[] queryParams = getSaveParameters(entity);
        Long id = executor.executeInsert(getSaveQuery(), queryParams);
        entity.setId(id);
    }

    @Override
    public void update(T entity) throws DaoException {
        Object[] queryParams = getUpdateParameters(entity);
        executor.executeUpdate(getUpdateQuery(), queryParams);
    }

    @Override
    public void delete(T entity) throws DaoException {
        executor.executeUpdate(getDeleteQuery(), entity.getId());
    }

    @Override
    public void deleteById(long id) throws DaoException {
        executor.executeUpdate(getDeleteQuery(), id);
    }

    @Override
    public List<T> findAll() throws DaoException {
        return executor.executeAndFetchAll(getSelectAllQuery());
    }

    @Override
    public Optional<T> findById(long id) throws DaoException {
        return executor.executeAndFetchOne(getSelectByIdQuery(), id);
    }

    @Override
    public List<T> findByPage(int perPage, int page) throws DaoException {
        if (page < 1 || perPage < 1) {
            throw new IllegalArgumentException("page and perPage parameters must be above zero");
        }
        int startingFrom = perPage * (page - 1);
        return executor.executeAndFetchAll(getSelectAllPageQuery(), startingFrom, perPage);
    }

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSaveQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getSelectAllPageQuery();

    protected abstract Object[] getSaveParameters(T entity);

    protected abstract Object[] getUpdateParameters(T entity);

}

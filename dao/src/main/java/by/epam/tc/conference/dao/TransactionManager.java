package by.epam.tc.conference.dao;

public interface TransactionManager {

    public void startTransaction() throws DaoException;

    public void commit() throws DaoException;

    public void rollback() throws DaoException;
}

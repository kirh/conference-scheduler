package by.epam.tc.conference.dao;

/**
 * Exposes transaction management operations
 */
public interface TransactionManager {

    /**
     * Starts transaction
     * @throws DaoException when connection error occurs
     */
    public void startTransaction() throws DaoException;

    /**
     * Makes all changes made since the startTransaction permanent
     * If this method was invoked from another transaction context, then it will do nothing
     * @throws DaoException when connection error occurs
     */
    public void commit() throws DaoException;

    /**
     * Undoes all changes made in the current transaction and all other transaction context
     * @throws DaoException when connection error occurs
     */
    public void rollback() throws DaoException;
}

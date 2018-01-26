package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * Contains logic to perform base crud operations on Identifiable objects
 * @param <T> the type of Identifiable
 */

public interface GenericDao<T extends Identifiable> {

    /**
     * Save entity to persistence data storage
     * @param entity to save
     * @throws DaoException when access error occurs
     */
    void save(T entity) throws DaoException;

    /**
     * Updates entity in persistence data storage
     * Id property should be specified.
     * @param entity to update
     * @throws DaoException when access error occurs
     */
    void update(T entity) throws DaoException;

    /**
     * Delete entity in underlying persistence data storage.
     * Id property should be specified.
     * @param entity to delete
     * @throws DaoException when access error occurs
     */
    void delete(T entity) throws DaoException;

    /**
     * Delete entity by identifier from underlying persistence data storage
     * @param id entity identifier
     * @throws DaoException when access error occurs
     */
    void deleteById(Long id) throws DaoException;

    /**
     * Returns list of all objects from underlying persistence data storage
     * @return list of all objects from underlying persistence data storage
     * @throws DaoException when access error occurs
     */
    List<T> findAll() throws DaoException;

    /**
     * Finds all objects from underlying persistence data storage by page, sorting by id.
     * @param perPage objects
     * @param page number
     * @return list of objects
     * @throws DaoException when access error occurs
     */
    List<T> findByPage(int perPage, int page) throws DaoException;

    /**
     * Finds object by identifier
     * @param id object identifier
     * @return Optional of object if was found or empty optional otherwise
     * @throws DaoException when access error occurs
     */
    Optional<T> findById(Long id) throws DaoException;

}

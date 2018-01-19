package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T extends Identifiable> {

    void save(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(T entity) throws DaoException;

    void deleteById(Long id) throws DaoException;

    List<T> findAll() throws DaoException;

    List<T> findAll(int perPage, int page) throws DaoException;

    Optional<T> findById(Long id) throws DaoException;

}

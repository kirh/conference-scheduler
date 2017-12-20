package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface GenericDao<K, V extends Identifiable<K>> {

    void save(V entity) throws DaoException;

    void update(V entity) throws DaoException;

    void delete(V entity) throws DaoException;

    List<V> findAll() throws DaoException;

    Optional<V> findById(K id) throws DaoException;

}

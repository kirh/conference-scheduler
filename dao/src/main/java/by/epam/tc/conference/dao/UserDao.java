package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.User;

import java.util.Optional;

/**
 * UserDao performs persistence operations with User
 */
public interface UserDao extends GenericDao<User> {

    public Optional<User> findByUserName(String username) throws DaoException;
}

package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.User;

import java.util.Optional;

/**
 * UserDao performs persistence operations with User
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Finds user by given username and returns optional user when found or empty optional otherwise
     * @param username
     * @return Optional user if was found or empty optional otherwise
     * @throws DaoException error during data access occurs
     */
    Optional<User> findByUserName(String username) throws DaoException;
}

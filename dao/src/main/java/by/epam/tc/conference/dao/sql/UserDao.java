package by.epam.tc.conference.dao.sql;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.GenericDao;
import by.epam.tc.conference.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    public Optional<User> findByUserNameAndPassword(String username, String password) throws DaoException;
}

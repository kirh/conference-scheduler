package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.commons.Md5Util;
import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.services.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    @Override
    public UserPrincipal authenticateUser(String username, String password) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findByUserName(username);
            User user = optionalUser.orElseThrow(() -> new ServiceException("User doesn't exist"));

            String inputPasswordHash = Md5Util.encode(password);
            String storedPasswordHash = user.getPassword();

            if (inputPasswordHash.equals(storedPasswordHash)) {
                logger.debug("User {} authenticated", username);
                return createUserPrincipal(user);
            } else {
                throw new ServiceException("Password mismatch");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private UserPrincipal createUserPrincipal(User user) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setAdmin(user.isAdmin());
        userPrincipal.setId(user.getId());
        logger.debug("Created userPrincipal id={} username={} isAdmin={}",
                userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.isAdmin());
        return userPrincipal;
    }

    @Override
    public UserPrincipal registerUser(User user) throws ServiceException {
        boolean isValid = userValidator.validateUser(user);
        if (isValid) {
            try {
                userDao.save(user);
                logger.info("Registered user id={} username={}", user.getId(), user.getUsername());
                return createUserPrincipal(user);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        throw new ServiceException("Invalid user");
    }
}

package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.AuthenticationException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.services.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Function;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final Validator<User> validator;
    private final Function<String, String> passwordEncoder;

    public UserServiceImpl(UserDao userDao, Validator<User> validator, Function<String,String> passwordEncoder) {
        this.userDao = userDao;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserPrincipal authenticateUser(String username, String password) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findByUsername(username);
            User user = optionalUser.orElseThrow(() -> new AuthenticationException("User doesn't exist"));

            String inputPasswordHash = passwordEncoder.apply(password);
            String storedPasswordHash = user.getPassword();

            if (inputPasswordHash.equals(storedPasswordHash)) {
                logger.debug("User {} authenticated", username);
                return createUserPrincipal(user);
            } else {
                throw new AuthenticationException("Password mismatch");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private UserPrincipal createUserPrincipal(User user) {
        Long userId = user.getId();
        String username = user.getUsername();
        boolean admin = user.isAdmin();
        UserPrincipal userPrincipal = new UserPrincipal(userId, username, admin);
        logger.debug("Created userPrincipal {}", userPrincipal);
        return userPrincipal;
    }

    @Override
    public UserPrincipal registerUser(User user) throws ServiceException {
        boolean isValid = validator.validate(user);
        if (!isValid) {
            throw new InvalidDataException("invalid user: " + user);
        }
        try {
            String username = user.getUsername();
            Optional<User> optionalUserWithSameUsername = userDao.findByUsername(username);
            if (optionalUserWithSameUsername.isPresent()) {
                throw new AlreadyExistsException("User with username " + username + "already exists");
            }
            userDao.save(user);
            logger.info("Registered user id={} username={}", user.getId(), user.getUsername());
            return createUserPrincipal(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

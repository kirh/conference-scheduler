package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.AuthenticationException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;

/**
 * Base user operations
 */
public interface UserService {

    /**
     * Authenticates user with provided username and password
     * @param username to authenticate
     * @param password related to given username
     * @return UserPrincipal that represents current user
     * @throws AuthenticationException when user with such username doesn't exist or password mismatch
     * @throws ServiceException when error during data access occurs
     */
    UserPrincipal authenticateUser(String username, String password) throws ServiceException;

    /**
     * Creates new user
     * @param user contains user information
     * @return UserPrincipal that represents registered user
     * @throws AlreadyExistsException when username already in use
     * @throws InvalidDataException when user is invalid
     * @throws ServiceException when error during data access occurs
     */
    UserPrincipal registerUser(User user) throws ServiceException;
}

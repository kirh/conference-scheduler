package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.AuthenticationException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;

public interface UserService {

    UserPrincipal authenticateUser(String username, String password) throws AuthenticationException, ServiceException;

    UserPrincipal registerUser(User user) throws InvalidEntityException, AlreadyExistsException, ServiceException;
}

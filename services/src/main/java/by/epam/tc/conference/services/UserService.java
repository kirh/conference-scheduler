package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;

public interface UserService {

    UserPrincipal authenticateUser(String username, String password) throws ServiceException;

    UserPrincipal registerUser(User user) throws ServiceException;
}

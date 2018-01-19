package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.commons.Md5Util;
import by.epam.tc.conference.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserBuilder implements Builder<User> {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String IS_ADMIN = "admin";

    public User build(HttpServletRequest request) {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        String hashedPassword = Md5Util.encode(password);
        String email = request.getParameter(EMAIL);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String isAdminLine = request.getParameter(IS_ADMIN);
        Boolean isAdmin = Boolean.valueOf(isAdminLine);
        return new User(username, hashedPassword, email, firstName, lastName, isAdmin);
    }
}

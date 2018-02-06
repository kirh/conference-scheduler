package by.epam.tc.conference.web.controller.command.builder;

import by.epam.tc.conference.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserBuilder implements Builder<User> {

    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";
    private static final String EMAIL_PARAM = "email";
    private static final String FIRST_NAME_PARAM = "firstName";
    private static final String LAST_NAME_PARAM = "lastName";
    private static final String IS_ADMIN_PARAM = "admin";

    public User build(HttpServletRequest request) {
        String username = request.getParameter(USERNAME_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        String email = request.getParameter(EMAIL_PARAM);
        String firstName = request.getParameter(FIRST_NAME_PARAM);
        String lastName = request.getParameter(LAST_NAME_PARAM);
        String isAdminLine = request.getParameter(IS_ADMIN_PARAM);
        Boolean isAdmin = Boolean.valueOf(isAdminLine);
        return new User(username, password, email, firstName, lastName, isAdmin);
    }
}

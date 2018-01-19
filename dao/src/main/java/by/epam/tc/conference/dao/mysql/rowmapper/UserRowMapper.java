package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    private static final String ID = "u_id";
    private static final String USERNAME = "u_username";
    private static final String PASSWORD = "u_password";
    private static final String EMAIL = "u_email";
    private static final String FIRST_NAME = "u_first_name";
    private static final String LAST_NAME = "u_last_name";
    private static final String IS_ADMIN = "u_is_admin";

    @Override
    public User handle(ResultSet resultSet) throws SQLException {
        User user = new User();

        long id = resultSet.getLong(ID);
        user.setId(id);

        String username = resultSet.getString(USERNAME);
        user.setUsername(username);

        String password = resultSet.getString(PASSWORD);
        user.setPassword(password);

        String email = resultSet.getString(EMAIL);
        user.setEmail(email);

        String firstName = resultSet.getString(FIRST_NAME);
        user.setFirstName(firstName);

        String lastName = resultSet.getString(LAST_NAME);
        user.setLastName(lastName);

        boolean isAdmin = resultSet.getBoolean(IS_ADMIN);
        user.setAdmin(isAdmin);

        return user;
    }
}

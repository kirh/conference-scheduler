package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    private static final String ID_COLUMN = "u_id";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String PASSWORD_COLUMN = "u_password";
    private static final String EMAIL_COLUMN = "u_email";
    private static final String FIRST_NAME_COLUMN = "u_first_name";
    private static final String LAST_NAME_COLUMN = "u_last_name";
    private static final String IS_ADMIN_COLUMN = "u_is_admin";

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String username = resultSet.getString(USERNAME_COLUMN);
        String password = resultSet.getString(PASSWORD_COLUMN);
        String email = resultSet.getString(EMAIL_COLUMN);
        String firstName = resultSet.getString(FIRST_NAME_COLUMN);
        String lastName = resultSet.getString(LAST_NAME_COLUMN);
        boolean isAdmin = resultSet.getBoolean(IS_ADMIN_COLUMN);
        return new User(id, username, password, email, firstName, lastName, isAdmin);
    }
}

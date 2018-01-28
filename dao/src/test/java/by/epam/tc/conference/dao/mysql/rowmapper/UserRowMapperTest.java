package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRowMapperTest {

    private static final String ID_COLUMN = "u_id";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String PASSWORD_COLUMN = "u_password";
    private static final String EMAIL_COLUMN = "u_email";
    private static final String FIRST_NAME_COLUMN = "u_first_name";
    private static final String LAST_NAME_COLUMN = "u_last_name";
    private static final String IS_ADMIN_COLUMN = "u_is_admin";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildUserWhenResultSetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(USERNAME_COLUMN)).thenReturn("user");
        when(resultSet.getString(PASSWORD_COLUMN)).thenReturn("password");
        when(resultSet.getString(EMAIL_COLUMN)).thenReturn("user@email.ru");
        when(resultSet.getString(FIRST_NAME_COLUMN)).thenReturn("vasya");
        when(resultSet.getString(LAST_NAME_COLUMN)).thenReturn("pupkin");
        when(resultSet.getBoolean(IS_ADMIN_COLUMN)).thenReturn(true);

        UserRowMapper rowMapper = new UserRowMapper();
        User actual = rowMapper.map(resultSet);

        User expected = new User(1L, "user", "password", "user@email.ru", "vasya", "pupkin", true);
        
        assertEquals(actual, expected);
    }
}
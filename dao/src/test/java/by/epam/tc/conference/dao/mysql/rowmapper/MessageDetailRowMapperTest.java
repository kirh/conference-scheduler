package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.MessageDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageDetailRowMapperTest {

    private static final String ID_COLUMN = "m_id";
    private static final String TEXT_COLUMN = "m_text";
    private static final String QUESTION_ID_COLUMN = "m_q_id";
    private static final String USER_ID_COLUMN = "m_u_id";
    private static final String CREATE_TIME_COLUMN = "m_create_time";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String IS_ADMIN_COLUMN = "u_is_admin";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildMessageDetailFromResultSetWhenBuild() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TEXT_COLUMN)).thenReturn("text");
        when(resultSet.getLong(QUESTION_ID_COLUMN)).thenReturn(2L);
        when(resultSet.getLong(USER_ID_COLUMN)).thenReturn(3L);
        Timestamp timestamp = Timestamp.from(Instant.now());
        when(resultSet.getTimestamp(CREATE_TIME_COLUMN)).thenReturn(timestamp);
        when(resultSet.getString(USERNAME_COLUMN)).thenReturn("name");
        when(resultSet.getBoolean(IS_ADMIN_COLUMN)).thenReturn(true);

        MessageDetailRowMapper rowMapper = new MessageDetailRowMapper();
        MessageDetails actual = rowMapper.map(resultSet);

        assertEquals(actual, new MessageDetails(1L, "text", 2L, 3L, "name", timestamp, true));
    }
}
package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageRowMapperTest {

    private static final String ID_COLUMN = "m_id";
    private static final String TEXT_COLUMN = "m_text";
    private static final String QUESTION_ID_COLUMN = "m_q_id";
    private static final String USER_ID_COLUMN = "m_u_id";
    private static final String CREATE_TIME_COLUMN = "m_create_time";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildMessageWhenResultSetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TEXT_COLUMN)).thenReturn("text");
        when(resultSet.getLong(QUESTION_ID_COLUMN)).thenReturn(2L);
        when(resultSet.getLong(USER_ID_COLUMN)).thenReturn(3L);
        Timestamp timestamp = Timestamp.from(Instant.now());
        when(resultSet.getTimestamp(CREATE_TIME_COLUMN)).thenReturn(timestamp);

        MessageRowMapper rowMapper = new MessageRowMapper();
        Message actual = rowMapper.map(resultSet);

        assertEquals(actual, new Message(1L, "text", 2L, 3L));
    }
}
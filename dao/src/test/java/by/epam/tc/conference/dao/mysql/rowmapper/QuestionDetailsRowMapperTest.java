package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.QuestionDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionDetailsRowMapperTest {

    private static final String ID_COLUMN = "q_id";
    private static final String TEXT_COLUMN = "q_text";
    private static final String USER_ID_COLUMN = "q_u_id";
    private static final String CONFERENCE_ID_COLUMN = "q_c_id";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String CONFERENCE_NAME_COLUMN = "c_name";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildQuestionDetailsWhenResultSetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TEXT_COLUMN)).thenReturn("text");
        when(resultSet.getLong(USER_ID_COLUMN)).thenReturn(2L);
        when(resultSet.getLong(CONFERENCE_ID_COLUMN)).thenReturn(3L);
        when(resultSet.getString(USERNAME_COLUMN)).thenReturn("user");
        when(resultSet.getString(CONFERENCE_NAME_COLUMN)).thenReturn("conference");

        QuestionDetailsRowMapper rowMapper = new QuestionDetailsRowMapper();
        QuestionDetails actual = rowMapper.map(resultSet);

        QuestionDetails expected = new QuestionDetails(1L, "text", 2L, 3L, "user", "conference");

        assertEquals(actual, expected);
    }
}
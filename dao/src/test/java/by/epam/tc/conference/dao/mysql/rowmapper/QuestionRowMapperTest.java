package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Question;
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
public class QuestionRowMapperTest {

    private static final String ID_COLUMN = "q_id";
    private static final String TEXT_COLUMN = "q_text";
    private static final String USER_ID_COLUMN = "q_u_id";
    private static final String CONFERENCE_ID_COLUMN = "q_c_id";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuldQuestionWhenResultSetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TEXT_COLUMN)).thenReturn("question");
        when(resultSet.getLong(USER_ID_COLUMN)).thenReturn(2L);
        when(resultSet.getLong(CONFERENCE_ID_COLUMN)).thenReturn(3L);

        QuestionRowMapper rowMapper = new QuestionRowMapper();
        Question actual = rowMapper.map(resultSet);

        assertEquals(actual, new Question(1L, "question", 2L, 3L));
    }
}
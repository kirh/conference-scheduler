package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Section;
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
public class SectionRowMapperTest {

    private static final String ID_COLUMN = "s_id";
    private static final String TOPIC_COLUMN = "s_topic";
    private static final String CONFERENCE_ID_COLUMN = "s_c_id";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildTopicWhenResultSetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TOPIC_COLUMN)).thenReturn("topic");
        when(resultSet.getLong(CONFERENCE_ID_COLUMN)).thenReturn(2L);

        SectionRowMapper rowMapper = new SectionRowMapper();
        Section actual = rowMapper.map(resultSet);
        
        assertEquals(actual, new Section(1L, "topic", 2L ));
    }
}
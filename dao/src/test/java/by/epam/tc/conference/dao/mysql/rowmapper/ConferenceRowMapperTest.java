package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Conference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRowMapperTest {

    private static final String ID_COLUMN = "c_id";
    private static final String NAME_COLUMN = "c_name";
    private static final String ADDRESS_COLUMN = "c_address";
    private static final String ADMINISTRATOR_ID_COLUMN = "c_u_id";
    private static final String DATE_COLUMN = "c_datetime";
    private static final String DESCRIPTION_COLUMN = "c_description";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildConferenceFromResultSetRowWhenMap() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(NAME_COLUMN)).thenReturn("conf");
        when(resultSet.getString(ADDRESS_COLUMN)).thenReturn("moscow");
        when(resultSet.getString(DESCRIPTION_COLUMN)).thenReturn("description");
        when(resultSet.getLong(ADMINISTRATOR_ID_COLUMN)).thenReturn(2L);
        Date date = new Date(2018, 1, 1);
        when(resultSet.getDate(DATE_COLUMN)).thenReturn(date);

        ConferenceRowMapper rowMapper = new ConferenceRowMapper();
        Conference actual = rowMapper.map(resultSet);

        Conference expected = new Conference(1L, "conf", "description", "moscow", date, 2L);
        assertEquals(actual, expected);
    }
}
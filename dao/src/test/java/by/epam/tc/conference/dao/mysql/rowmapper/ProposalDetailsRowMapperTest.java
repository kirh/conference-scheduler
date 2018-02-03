package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.ProposalStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProposalDetailsRowMapperTest {

    private static final String ID_COLUMN = "p_id";
    private static final String TITLE_COLUMN = "p_title";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String CONFERENCE_NAME_COLUMN = "c_name";
    private static final String SECTION_TOPIC_COLUMN = "s_topic";
    private static final String STATUS_COLUMN = "p_status";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildProposalDetailsWhenResultsetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TITLE_COLUMN)).thenReturn("proposal");
        when(resultSet.getString(USERNAME_COLUMN)).thenReturn("user");
        when(resultSet.getString(CONFERENCE_NAME_COLUMN)).thenReturn("conference");
        when(resultSet.getString(SECTION_TOPIC_COLUMN)).thenReturn("topic");
        when(resultSet.getString(STATUS_COLUMN)).thenReturn("approved");

        ProposalDetailsRowMapper rowMapper = new ProposalDetailsRowMapper();
        ProposalDetails actual = rowMapper.map(resultSet);
        
        ProposalDetails expected = new ProposalDetails(1L, "proposal", "user", "conference", "topic", ProposalStatus.APPROVED);

        assertEquals(actual, expected);
    }
}
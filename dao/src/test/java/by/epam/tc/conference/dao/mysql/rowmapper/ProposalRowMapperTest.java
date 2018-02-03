package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Proposal;
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
public class ProposalRowMapperTest {

    private static final String ID_COLUMN = "p_id";
    private static final String TITLE_COLUMN = "p_title";
    private static final String DESCRIPTION_COLUMN = "p_description";
    private static final String PARTICIPANT_ID_COLUMN = "p_u_id";
    private static final String SECTION_ID_COLUMN = "p_s_id";
    private static final String STATUS_COLUMN = "p_status";

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldBuildProposalWhenResultSetGiven() throws SQLException {
        when(resultSet.getLong(ID_COLUMN)).thenReturn(1L);
        when(resultSet.getString(TITLE_COLUMN)).thenReturn("proposal");
        when(resultSet.getString(DESCRIPTION_COLUMN)).thenReturn("description");
        when(resultSet.getLong(PARTICIPANT_ID_COLUMN)).thenReturn(2L);
        when(resultSet.getLong(SECTION_ID_COLUMN)).thenReturn(3L);
        when(resultSet.getString(STATUS_COLUMN)).thenReturn("approved");

        ProposalRowMapper rowMapper = new ProposalRowMapper();
        Proposal actual = rowMapper.map(resultSet);
        
        Proposal expected = new Proposal(1L, "proposal", "description", 3L, 2L, ProposalStatus.APPROVED);

        assertEquals(actual, expected);
    }
}
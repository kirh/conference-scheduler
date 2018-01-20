package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProposalRowMapper implements RowMapper<Proposal> {

    private static final String ID = "p_id";
    private static final String TITLE = "p_title";
    private static final String DESCRIPTION = "p_description";
    private static final String PARTICIPANT_ID = "p_u_id";
    private static final String SECTION_ID = "p_s_id";
    private static final String STATUS = "p_status";

    @Override
    public Proposal map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String title = resultSet.getString(TITLE);
        String description = resultSet.getString(DESCRIPTION);
        long participantId = resultSet.getLong(PARTICIPANT_ID);
        long sectionId = resultSet.getLong(SECTION_ID);
        String statusLine = resultSet.getString(STATUS);
        ProposalStatus status = ProposalStatus.valueOf(statusLine.toUpperCase());
        return new Proposal(id, title, description, sectionId, participantId, status);
    }
}

package by.epam.tc.conference.dao.sql.rowmapper;

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
    public Proposal handle(ResultSet resultSet) throws SQLException {
        Proposal proposal = new Proposal();

        long id = resultSet.getLong(ID);
        proposal.setId(id);

        String title = resultSet.getString(TITLE);
        proposal.setTitle(title);

        String description = resultSet.getString(DESCRIPTION);
        proposal.setDescription(description);

        long participantId = resultSet.getLong(PARTICIPANT_ID);
        proposal.setParticipantId(participantId);

        long sectionId = resultSet.getLong(SECTION_ID);
        proposal.setSectionId(sectionId);

        String statusLine = resultSet.getString(STATUS);
        ProposalStatus status = ProposalStatus.valueOf(statusLine);
        proposal.setStatus(status);

        return proposal;
    }
}

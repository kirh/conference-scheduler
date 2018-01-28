package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProposalRowMapper implements RowMapper<Proposal> {

    private static final String ID_COLUMN = "p_id";
    private static final String TITLE_COLUMN = "p_title";
    private static final String DESCRIPTION_COLUMN = "p_description";
    private static final String PARTICIPANT_ID_COLUMN = "p_u_id";
    private static final String SECTION_ID_COLUMN = "p_s_id";
    private static final String STATUS_COLUMN = "p_status";

    @Override
    public Proposal map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String title = resultSet.getString(TITLE_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        long participantId = resultSet.getLong(PARTICIPANT_ID_COLUMN);
        long sectionId = resultSet.getLong(SECTION_ID_COLUMN);
        String statusLine = resultSet.getString(STATUS_COLUMN);
        ProposalStatus status = ProposalStatus.valueOf(statusLine.toUpperCase());
        return new Proposal(id, title, description, sectionId, participantId, status);
    }
}

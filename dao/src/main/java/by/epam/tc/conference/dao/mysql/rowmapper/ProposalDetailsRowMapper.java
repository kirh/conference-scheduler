package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.ProposalStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProposalDetailsRowMapper implements RowMapper<ProposalDetails> {

    private static final String ID_COLUMN = "p_id";
    private static final String TITLE_COLUMN = "p_title";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String CONFERENCE_NAME_COLUMN = "c_name";
    private static final String SECTION_TOPIC_COLUMN = "s_topic";
    private static final String STATUS_COLUMN = "p_status";

    @Override
    public ProposalDetails map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String title = resultSet.getString(TITLE_COLUMN);
        String username = resultSet.getString(USERNAME_COLUMN);
        String conferenceName = resultSet.getString(CONFERENCE_NAME_COLUMN);
        String sectionTopic = resultSet.getString(SECTION_TOPIC_COLUMN);
        String statusString = resultSet.getString(STATUS_COLUMN);
        ProposalStatus status = ProposalStatus.valueOf(statusString.toUpperCase());
        return new ProposalDetails(id, title, username, conferenceName, sectionTopic, status);
    }
}

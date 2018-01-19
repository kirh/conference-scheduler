package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.ProposalStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProposalDetailsRowMapper implements RowMapper<ProposalDetails>{

    private static final String ID = "p_id";
    private static final String TITLE = "p_title";
    private static final String USERNAME = "u_username";
    private static final String CONFERENCE_NAME = "c_name";
    private static final String SECTION_TOPIC = "s_topic";
    private static final String STATUS = "p_status";

    @Override
    public ProposalDetails handle(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String title = resultSet.getString(TITLE);
        String username = resultSet.getString(USERNAME);
        String conferenceName = resultSet.getString(CONFERENCE_NAME);
        String sectionTopic = resultSet.getString(SECTION_TOPIC);
        String statusString = resultSet.getString(STATUS);
        ProposalStatus status = ProposalStatus.valueOf(statusString.toUpperCase());
        return new ProposalDetails(id, title, username, conferenceName, sectionTopic, status);
    }
}

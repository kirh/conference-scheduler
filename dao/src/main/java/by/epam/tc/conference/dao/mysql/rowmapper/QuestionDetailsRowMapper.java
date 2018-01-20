package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.QuestionDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionDetailsRowMapper implements RowMapper<QuestionDetails> {

    private static final String ID = "q_id";
    private static final String TEXT = "q_text";
    private static final String USER_ID = "q_u_id";
    private static final String CONFERENCE_ID = "q_c_id";
    private static final String USERNAME = "u_username";
    private static final String CONFERENCE_NAME = "c_name";

    @Override
    public QuestionDetails handle(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String text = resultSet.getString(TEXT);
        long userId = resultSet.getLong(USER_ID);
        long conferenceId = resultSet.getLong(CONFERENCE_ID);
        String username = resultSet.getString(USERNAME);
        String conferenceName = resultSet.getString(CONFERENCE_NAME);
        return new QuestionDetails(id, text, userId, conferenceId, username, conferenceName);
    }
}

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
        QuestionDetails questionDetails = new QuestionDetails();

        long id = resultSet.getLong(ID);
        questionDetails.setId(id);

        String text = resultSet.getString(TEXT);
        questionDetails.setTitle(text);

        long userId = resultSet.getLong(USER_ID);
        questionDetails.setUserId(userId);

        long conferenceId = resultSet.getLong(CONFERENCE_ID);
        questionDetails.setConferenceId(conferenceId);

        String username = resultSet.getString(USERNAME);
        questionDetails.setUsername(username);

        String conferenceName = resultSet.getString(CONFERENCE_NAME);
        questionDetails.setConferenceName(conferenceName);

        return questionDetails;
    }
}

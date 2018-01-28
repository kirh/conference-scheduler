package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.QuestionDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionDetailsRowMapper implements RowMapper<QuestionDetails> {

    private static final String ID_COLUMN = "q_id";
    private static final String TEXT_COLUMN = "q_text";
    private static final String USER_ID_COLUMN = "q_u_id";
    private static final String CONFERENCE_ID_COLUMN = "q_c_id";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String CONFERENCE_NAME_COLUMN = "c_name";

    @Override
    public QuestionDetails map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String text = resultSet.getString(TEXT_COLUMN);
        long userId = resultSet.getLong(USER_ID_COLUMN);
        long conferenceId = resultSet.getLong(CONFERENCE_ID_COLUMN);
        String username = resultSet.getString(USERNAME_COLUMN);
        String conferenceName = resultSet.getString(CONFERENCE_NAME_COLUMN);
        return new QuestionDetails(id, text, userId, conferenceId, username, conferenceName);
    }
}

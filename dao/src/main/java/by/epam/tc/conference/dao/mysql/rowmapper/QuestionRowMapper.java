package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {

    private static final String ID_COLUMN = "q_id";
    private static final String TEXT_COLUMN = "q_text";
    private static final String USER_ID_COLUMN = "q_u_id";
    private static final String CONFERENCE_ID_COLUMN = "q_c_id";

    @Override
    public Question map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String text = resultSet.getString(TEXT_COLUMN);
        long userId = resultSet.getLong(USER_ID_COLUMN);
        long conferenceId = resultSet.getLong(CONFERENCE_ID_COLUMN);
        return new Question(id, text, userId, conferenceId);
    }
}

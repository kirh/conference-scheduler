package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

public class QuestionRowMapper implements RowMapper<Question> {

    private static final String ID = "q_id";
    private static final String TEXT = "q_text";
    private static final String USER_ID = "q_u_id";
    private static final String CONFERENCE_ID = "q_c_id";

    @Override
    public Question handle(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String text = resultSet.getString(TEXT);
        long userId = resultSet.getLong(USER_ID);
        long conferenceId = resultSet.getLong(CONFERENCE_ID);
        return new Question(id, text, userId, conferenceId);
    }
}

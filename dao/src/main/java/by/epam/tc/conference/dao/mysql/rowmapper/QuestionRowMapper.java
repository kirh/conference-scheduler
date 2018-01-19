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
        Question question = new Question();

        long id = resultSet.getLong(ID);
        question.setId(id);

        String text = resultSet.getString(TEXT);
        question.setTitle(text);

        long userId = resultSet.getLong(USER_ID);
        question.setUserId(userId);

        long conferenceId = resultSet.getLong(CONFERENCE_ID);
        question.setConferenceId(conferenceId);

        return question;
    }
}

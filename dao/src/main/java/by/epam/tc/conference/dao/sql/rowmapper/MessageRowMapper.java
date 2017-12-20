package by.epam.tc.conference.dao.sql.rowmapper;

import by.epam.tc.conference.entity.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MessageRowMapper implements RowMapper<Message> {

    private static final String ID = "m_id";
    private static final String TEXT = "m_text";
    private static final String QUESTION_ID = "m_q_id";
    private static final String USER_ID = "m_u_id";
    private static final String CREATE_TIME = "m_create_time";

    @Override
    public Message handle(ResultSet resultSet) throws SQLException {
        Message message = new Message();

        long id = resultSet.getLong(ID);
        message.setId(id);

        String text = resultSet.getString(TEXT);
        message.setText(text);

        long questionId = resultSet.getLong(QUESTION_ID);
        message.setQuestionId(questionId);

        long userId = resultSet.getLong(USER_ID);
        message.setUserId(userId);

        Timestamp createTime = resultSet.getTimestamp(CREATE_TIME);
        message.setCreateTime(createTime);

        return message;
    }
}

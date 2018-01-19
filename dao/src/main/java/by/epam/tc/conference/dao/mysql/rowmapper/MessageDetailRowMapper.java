package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.MessageDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MessageDetailRowMapper implements RowMapper<MessageDetails> {

    private static final String ID = "m_id";
    private static final String TEXT = "m_text";
    private static final String QUESTION_ID = "m_q_id";
    private static final String USER_ID = "m_u_id";
    private static final String CREATE_TIME = "m_create_time";

    @Override
    public MessageDetails handle(ResultSet resultSet) throws SQLException {
        MessageDetails messageDetails = new MessageDetails();

        long id = resultSet.getLong(ID);
        messageDetails.setId(id);

        String text = resultSet.getString(TEXT);
        messageDetails.setText(text);

        long questionId = resultSet.getLong(QUESTION_ID);
        messageDetails.setQuestionId(questionId);

        long userId = resultSet.getLong(USER_ID);
        messageDetails.setUserId(userId);

        Timestamp createTime = resultSet.getTimestamp(CREATE_TIME);
        messageDetails.setCreateTime(createTime);

        String username = resultSet.getString("u_username");
        messageDetails.setUsername(username);

        boolean sendByAdmin = resultSet.getBoolean("u_is_admin");
        messageDetails.setSendByAdmin(sendByAdmin);

        return messageDetails;
    }
}

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
    public MessageDetails map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String text = resultSet.getString(TEXT);
        long questionId = resultSet.getLong(QUESTION_ID);
        long userId = resultSet.getLong(USER_ID);
        Timestamp createTime = resultSet.getTimestamp(CREATE_TIME);
        String username = resultSet.getString("u_username");
        boolean sendByAdmin = resultSet.getBoolean("u_is_admin");
        return new MessageDetails(id, text, questionId, userId, username, sendByAdmin);
    }
}

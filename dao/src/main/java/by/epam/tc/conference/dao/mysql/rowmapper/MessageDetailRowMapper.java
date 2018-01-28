package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.dto.MessageDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MessageDetailRowMapper implements RowMapper<MessageDetails> {

    private static final String ID_COLUMN = "m_id";
    private static final String TEXT_COLUMN = "m_text";
    private static final String QUESTION_ID_COLUMN = "m_q_id";
    private static final String USER_ID_COLUMN = "m_u_id";
    private static final String CREATE_TIME_COLUMN = "m_create_time";
    private static final String USERNAME_COLUMN = "u_username";
    private static final String IS_ADMIN_COLUMN = "u_is_admin";

    @Override
    public MessageDetails map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String text = resultSet.getString(TEXT_COLUMN);
        long questionId = resultSet.getLong(QUESTION_ID_COLUMN);
        long userId = resultSet.getLong(USER_ID_COLUMN);
        Timestamp createTime = resultSet.getTimestamp(CREATE_TIME_COLUMN);
        String username = resultSet.getString(USERNAME_COLUMN);
        boolean sendByAdmin = resultSet.getBoolean(IS_ADMIN_COLUMN);
        return new MessageDetails(id, text, questionId, userId, username, sendByAdmin);
    }
}

package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.entity.Message;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDao extends AbstractDao<Message> {

    private static final String UPDATE = "update message set m_text=?, m_q_id=?, m_u_id=? where m_id=?";
    private static final String DELETE = "delete from message where m_id=?";
    private static final String SELECT = "select * from message where m_id=?";
    private static final String SELECT_ALL = "select * from message";
    private static final String SAVE = "insert into message (m_text, m_q_id, m_u_id) values(?, ?, ?)";

    public MessageDao(ResultHandler<Message> resultHandler, Executor<Message> executor) {
        super(resultHandler, executor);
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getSaveQuery() {
        return SAVE;
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT;
    }

    @Override
    protected void configureSaveStatement(Message message, PreparedStatement statement) throws SQLException {
        statement.setString(0, message.getText());
        statement.setLong(1, message.getQuestionId());
        statement.setLong(2, message.getUserId());
    }

    @Override
    protected void configureUpdateStatement(Message message, PreparedStatement statement) throws SQLException {
        configureSaveStatement(message, statement);
        statement.setLong(3, message.getId());
    }
}

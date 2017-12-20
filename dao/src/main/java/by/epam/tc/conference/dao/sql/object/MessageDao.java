package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.object.AbstractDao;
import by.epam.tc.conference.entity.Message;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDao extends AbstractDao<Long, Message> {

    private static final String UPDATE = "update message set m_text=?, m_q_id=?, m_u_id=? where m_id=?";
    private static final String DELETE = "delete from message where m_id=?";
    private static final String SELECT = "select * from message where m_id=?";
    private static final String SELECT_ALL = "select * from message";
    private static final String SAVE = "insert into message (m_text, m_q_id, m_u_id) values(?, ?, ?)";

    public MessageDao(ResultHandler<Message> resultHandler, Executor<Long, Message> executor) {
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
    protected void configureUpdateStatement(Message entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getText());
        statement.setLong(1, entity.getQuestionId());
        statement.setLong(2, entity.getUserId());
        statement.setLong(3, entity.getId());
    }

    @Override
    protected void configureSelectByIdStatement(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    @Override
    protected void configureSaveStatement(Message entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getText());
        statement.setLong(1, entity.getQuestionId());
        statement.setLong(2, entity.getUserId());
    }

    @Override
    protected void configureDeleteStatement(Message entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());

    }
}

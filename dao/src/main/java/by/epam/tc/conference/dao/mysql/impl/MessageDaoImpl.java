package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.MessageDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.Message;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {

    private static final String UPDATE = "update message set m_text=?, m_q_id=?, m_u_id=? where m_id=?";
    private static final String DELETE = "delete from message where m_id=?";
    private static final String SELECT = "select m_id, m_text, m_q_id, m_u_id, m_create_time from message where m_id=?";
    private static final String SELECT_ALL = "select m_id, m_text, m_q_id, m_u_id, m_create_time from message";
    private static final String SAVE = "insert into message (m_text, m_q_id, m_u_id) values(?, ?, ?)";
    private static final String SELECT_ALL_PAGE = "select m_id, m_text, m_q_id, m_u_id, m_create_time"
            + " from message limit ?,? order by m_id";

    public MessageDaoImpl(Executor<Message> executor) {
        super(executor);
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
    protected String getSelectAllPageQuery() {
        return SELECT_ALL_PAGE;
    }

    @Override
    protected Object[] getSaveParameters(Message message) {
        return new Object[]{
                message.getText(),
                message.getQuestionId(),
                message.getUserId()
        };
    }

    @Override
    protected Object[] getUpdateParameters(Message message) {
        return new Object[]{
                message.getText(),
                message.getQuestionId(),
                message.getUserId(),
                message.getId()
        };
    }

}

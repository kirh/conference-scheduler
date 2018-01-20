package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.dao.MessageDetailsDao;
import by.epam.tc.conference.dto.MessageDetails;

import java.util.List;

public class MessageDetailsDaoImpl implements MessageDetailsDao {

    private static final String SELECT_BY_QUESTION_SQL = "select message.*, u_username, u_is_admin from message join " +
            "user on " +
            "m_u_id=u_id where " +
            "m_q_id=? order by m_id";

    private final Executor<MessageDetails> executor;

    public MessageDetailsDaoImpl(Executor<MessageDetails> executor) {
        this.executor = executor;
    }

    @Override
    public List<MessageDetails> findMessagesByQuestionId(Long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_QUESTION_SQL, id);
    }
}

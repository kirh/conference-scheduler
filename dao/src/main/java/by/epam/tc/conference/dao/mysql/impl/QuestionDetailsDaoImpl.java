package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.QuestionDetailsDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.dto.QuestionDetails;

import java.util.List;

public class QuestionDetailsDaoImpl implements QuestionDetailsDao {

    private static final String SELECT_BY_ADMINISTRATOR_ID_SQL = "select q_id, q_text, q_u_id, q_c_id, c_name,"
            + " p.u_username from question join conference on q_c_id=c_id join user admin on c_u_id=admin.u_id"
            + " join user p on q_u_id=p.u_id where admin.u_id=?";

    private static final String SELECT_BY_PARTICIPANT_ID_SQL = "select q_id, q_text, q_u_id, q_c_id, c_name, u_username"
            + " from question join conference on c_id=q_c_id join user on q_u_id=u_id where u_id=?";

    private final Executor<QuestionDetails> executor;

    public QuestionDetailsDaoImpl(Executor<QuestionDetails> executor) {
        this.executor = executor;
    }

    @Override
    public List<QuestionDetails> findQuestionsByAdminId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_ADMINISTRATOR_ID_SQL, id);
    }

    @Override
    public List<QuestionDetails> findQuestionsByParticipantId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_PARTICIPANT_ID_SQL, id);
    }
}

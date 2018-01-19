package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.QuestionDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.Question;

public class QuestionDaoImpl extends AbstractDao<Question> implements QuestionDao {


    private static final String UPDATE = "update question set q_text=?, q_u_id=?, q_c_id=? where q_id=?";
    private static final String DELETE = "delete from question where q_id=?";
    private static final String SELECT = "select * from question where q_id=?";
    private static final String SELECT_ALL = "select * from question";
    private static final String SAVE = "insert into question (q_text, q_u_id, q_c_id) values(?, ?, ?)";
    private static final String SELECT_ALL_PAGE = "select * from question limit ?,? order by q_id";

    public QuestionDaoImpl(Executor<Question> executor) {
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
    protected Object[] getSaveParameters(Question question) {
        return new Object[]{
                question.getTitle(),
                question.getUserId(),
                question.getConferenceId()
        };
    }

    @Override
    protected Object[] getUpdateParameters(Question question) {
        return new Object[]{
                question.getTitle(),
                question.getUserId(),
                question.getConferenceId(),
                question.getId()
        };
    }

}

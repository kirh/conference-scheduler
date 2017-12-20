package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.object.AbstractDao;
import by.epam.tc.conference.entity.Question;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestionDao extends AbstractDao<Long, Question> {

    private static final String UPDATE = "update question set q_text=?, q_u_id=?, q_c_id=? where q_id=?";
    private static final String DELETE = "delete from question where q_id=?";
    private static final String SELECT = "select * from question where q_id=?";
    private static final String SELECT_ALL = "select * from question";
    private static final String SAVE = "insert into question (q_text, q_u_id, q_c_id) values(?, ?, ?)";


    public QuestionDao(ResultHandler<Question> resultHandler, Executor<Long, Question> executor) {
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
    protected void configureUpdateStatement(Question entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getText());
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getConferenceId());
        statement.setLong(3, entity.getId());

    }

    @Override
    protected void configureSelectByIdStatement(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    @Override
    protected void configureSaveStatement(Question entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getText());
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getConferenceId());
    }

    @Override
    protected void configureDeleteStatement(Question entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());
    }
}

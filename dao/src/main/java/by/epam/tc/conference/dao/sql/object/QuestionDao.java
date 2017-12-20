package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.entity.Question;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestionDao extends AbstractDao<Question> {

    private static final String UPDATE = "update question set q_text=?, q_u_id=?, q_c_id=? where q_id=?";
    private static final String DELETE = "delete from question where q_id=?";
    private static final String SELECT = "select * from question where q_id=?";
    private static final String SELECT_ALL = "select * from question";
    private static final String SAVE = "insert into question (q_text, q_u_id, q_c_id) values(?, ?, ?)";


    public QuestionDao(ResultHandler<Question> resultHandler, Executor<Question> executor) {
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
    protected void configureSaveStatement(Question question, PreparedStatement statement) throws SQLException {
        statement.setString(0, question.getText());
        statement.setLong(1, question.getUserId());
        statement.setLong(2, question.getConferenceId());
    }

    @Override
    protected void configureUpdateStatement(Question question, PreparedStatement statement) throws SQLException {
        configureSaveStatement(question, statement);
        statement.setLong(3, question.getId());

    }
}

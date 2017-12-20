package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.object.AbstractDao;
import by.epam.tc.conference.entity.Section;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SectionDao extends AbstractDao<Long, Section> {

    private static final String UPDATE = "update section set s_topic=?, s_c_id=? where s_id=?";
    private static final String DELETE = "delete from section where s_id=?";
    private static final String SELECT = "select * from section where s_id=?";
    private static final String SELECT_ALL = "select * from section";
    private static final String SAVE = "insert into section (s_topic, s_c_id) values(?, ?)";

    public SectionDao(ResultHandler<Section> resultHandler, Executor<Long, Section> executor) {
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
    protected void configureUpdateStatement(Section entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getTopic());
        statement.setLong(1, entity.getConferenceId());
        statement.setLong(2, entity.getId());
    }

    @Override
    protected void configureSelectByIdStatement(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    @Override
    protected void configureSaveStatement(Section entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getTopic());
        statement.setLong(1, entity.getConferenceId());

    }

    @Override
    protected void configureDeleteStatement(Section entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());
    }
}

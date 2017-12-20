package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.entity.Section;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SectionDao extends AbstractDao<Section> {

    private static final String UPDATE = "update section set s_topic=?, s_c_id=? where s_id=?";
    private static final String DELETE = "delete from section where s_id=?";
    private static final String SELECT = "select * from section where s_id=?";
    private static final String SELECT_ALL = "select * from section";
    private static final String SAVE = "insert into section (s_topic, s_c_id) values(?, ?)";

    public SectionDao(ResultHandler<Section> resultHandler, Executor<Section> executor) {
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
    protected void configureSaveStatement(Section section, PreparedStatement statement) throws SQLException {
        statement.setString(0, section.getTopic());
        statement.setLong(1, section.getConferenceId());
    }

    @Override
    protected void configureUpdateStatement(Section section, PreparedStatement statement) throws SQLException {
        configureSaveStatement(section, statement);
        statement.setLong(2, section.getId());
    }
}

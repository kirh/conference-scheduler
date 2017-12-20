package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.object.AbstractDao;
import by.epam.tc.conference.entity.Conference;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConferenceDao extends AbstractDao<Long, Conference> {

    private static final String UPDATE = "update conference set c_name=?, c_address=?, c_date=?, c_u_id=? where c_id=?";
    private static final String DELETE = "delete from conference where c_id=?";
    private static final String SELECT = "select * from conference where c_id=?";
    private static final String SELECT_ALL = "select * from conference";
    private static final String SAVE = "insert into conference (c_name,c_address,c_date, c_u_id) values(?, ?, ?, ?)";

    public ConferenceDao(ResultHandler<Conference> resultHandler, Executor<Long, Conference> executor) {
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
    protected void configureUpdateStatement(Conference entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getName());
        statement.setString(1, entity.getAddress());
        java.util.Date date = entity.getDate();
        Date sqlDate = new Date(date.getTime());
        statement.setDate(2, sqlDate);
        statement.setLong(3, entity.getAdministratorId());
        statement.setLong(4, entity.getId());
    }

    @Override
    protected void configureSelectByIdStatement(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    @Override
    protected void configureSaveStatement(Conference entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getName());
        statement.setString(1, entity.getAddress());
        java.util.Date date = entity.getDate();
        Date sqlDate = new Date(date.getTime());
        statement.setDate(2, sqlDate);
        statement.setLong(3, entity.getAdministratorId());
    }

    @Override
    protected void configureDeleteStatement(Conference entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());
    }
}

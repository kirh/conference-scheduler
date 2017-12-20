package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.entity.Conference;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConferenceDao extends AbstractDao<Conference> {

    private static final String UPDATE = "update conference set c_name=?, c_address=?, c_date=?, c_u_id=? where c_id=?";
    private static final String DELETE = "delete from conference where c_id=?";
    private static final String SELECT = "select * from conference where c_id=?";
    private static final String SELECT_ALL = "select * from conference";
    private static final String SAVE = "insert into conference (c_name,c_address,c_date, c_u_id) values(?, ?, ?, ?)";

    public ConferenceDao(ResultHandler<Conference> resultHandler, Executor<Conference> executor) {
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
    protected void configureSaveStatement(Conference conference, PreparedStatement statement) throws SQLException {
        statement.setString(0, conference.getName());
        statement.setString(1, conference.getAddress());
        java.util.Date date = conference.getDate();
        Date sqlDate = new Date(date.getTime());
        statement.setDate(2, sqlDate);
        statement.setLong(3, conference.getAdministratorId());
    }

    @Override
    protected void configureUpdateStatement(Conference conference, PreparedStatement statement) throws SQLException {
        configureSaveStatement(conference,statement);
        statement.setLong(4, conference.getId());
    }
}

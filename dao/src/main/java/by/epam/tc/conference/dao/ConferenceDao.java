package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Conference;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConferenceDao extends AbstractDao<Long, Conference> {

    private static final String UPDATE = "update conference set c_name=?, c_address=?, c_date=? where c_id=?";
    private static final String DELETE = "delete from conference where c_id=?";
    private static final String SELECT = "select * from conference where c_id=?";
    private static final String SELECT_ALL = "select * from conference";
    private static final String SAVE = "insert into conference (c_name,c_address,c_date) values(?, ?, ?)";


    public ConferenceDao(Connection connection, ResultHandler<Conference> resultHandler) {
        super(connection, resultHandler);
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
    protected void configureUpdateStatement(PreparedStatement statement) {

    }

    @Override
    protected void configureSelectByIdStatement(PreparedStatement statement) {

    }

    @Override
    protected void configureSaveStatement(PreparedStatement statement) {

    }

    @Override
    protected void configureDeleteStatement(PreparedStatement statement) {

    }
}

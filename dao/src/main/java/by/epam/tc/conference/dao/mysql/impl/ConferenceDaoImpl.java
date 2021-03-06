package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.ConferenceDao;
import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.Conference;

import java.util.List;

public class ConferenceDaoImpl extends AbstractDao<Conference> implements ConferenceDao {

    private static final String UPDATE = "update conference set c_name=?, c_description=?, c_address=?, c_date=?,"
            + " c_u_id=? where c_id=?";
    private static final String DELETE = "delete from conference where c_id=?";
    private static final String SELECT = "select c_id, c_name, c_address, c_u_id, c_date, c_description from "
            + "conference where c_id=?";
    private static final String SELECT_ALL = "select c_id, c_name, c_address, c_u_id, c_date, c_description"
            + " from conference order by c_date";
    private static final String SELECT_ALL_ACTUAL = "select c_id, c_name, c_address, c_u_id, c_date, c_description"
            + " from conference where c_date > now() order by c_date";
    private static final String SAVE = "insert into conference (c_name, c_address, c_description, c_date, c_u_id)"
            + " values(?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USER_ID = "select c_id, c_name, c_address, c_u_id, c_date, c_description"
            + " from conference where c_u_id=?";
    private static final String SELECT_ALL_ACTUAL_PAGE = "select c_id, c_name, c_address, c_u_id, c_date,"
            + " c_description from conference limit ?,? where c_date > now() order by c_date asc";

    public ConferenceDaoImpl(Executor<Conference> executor) {
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
        return SELECT_ALL_ACTUAL_PAGE;
    }

    @Override
    protected Object[] getSaveParameters(Conference conference) {
        return new Object[]{
                conference.getName(),
                conference.getDescription(),
                conference.getAddress(),
                conference.getDate(),
                conference.getAdministratorId()
        };
    }

    @Override
    protected Object[] getUpdateParameters(Conference conference) {
        return new Object[]{
                conference.getName(),
                conference.getDescription(),
                conference.getAddress(),
                conference.getDate(),
                conference.getAdministratorId(),
                conference.getId()
        };
    }

    @Override
    public List<Conference> findConferencesByUserId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_USER_ID, id);
    }

    @Override
    public List<Conference> findAllActual() throws DaoException {
        return executor.executeAndFetchAll(SELECT_ALL_ACTUAL);
    }
}

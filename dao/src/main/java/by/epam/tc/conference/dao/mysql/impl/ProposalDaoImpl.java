package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import java.util.List;

public class ProposalDaoImpl extends AbstractDao<Proposal> implements ProposalDao {

    private static final String UPDATE = "update proposal set p_title=?, p_description=?, p_s_id=?, p_u_id=?, "
            + "p_status=? where p_id=?";
    private static final String DELETE = "delete from proposal where p_id=?";
    private static final String SELECT = "select p_id, p_title, p_description, p_u_id, p_s_id, p_status from proposal"
            + " where p_id=?";
    private static final String SELECT_ALL = "select p_id, p_title, p_description, p_u_id, p_s_id, p_status"
            + " from proposal";
    private static final String SAVE = "insert into proposal (p_title, p_description, p_s_id, p_u_id)"
            + " values(?, ?, ?, ?)";
    private static final String SELECT_BY_SECTION_ID = "select p_id, p_title, p_description, p_u_id, p_s_id, p_status"
            + " from proposal where p_s_id=?";
    private static final String SELECT_BY_USER_ID = "select p_id, p_title, p_description, p_u_id, p_s_id, p_status"
            + " from proposal where p_u_id=?";
    private static final String SELECT_ALL_PAGE = "select p_id, p_title, p_description, p_u_id, p_s_id, p_status"
            + " from proposal limit ?,? order by p_id";
    private static final String UPDATE_STATUS_SQL = "update proposal set p_status=? where p_id=?";

    public ProposalDaoImpl(Executor<Proposal> executor) {
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
    protected Object[] getSaveParameters(Proposal proposal) {
        return new Object[]{
                proposal.getTitle(),
                proposal.getDescription(),
                proposal.getSectionId(),
                proposal.getParticipantId()
        };
    }

    @Override
    protected Object[] getUpdateParameters(Proposal proposal) {
        return new Object[]{
                proposal.getTitle(),
                proposal.getDescription(),
                proposal.getSectionId(),
                proposal.getParticipantId(),
                proposal.getStatus().name(),
                proposal.getId()
        };
    }


    @Override
    public List<Proposal> findBySectionId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_SECTION_ID, id);
    }

    @Override
    public List<Proposal> findByUserId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_USER_ID, id);
    }

    @Override
    public void updateStatus(long id, ProposalStatus status) throws DaoException {
        executor.executeUpdate(UPDATE_STATUS_SQL, status.name(), id);
    }
}

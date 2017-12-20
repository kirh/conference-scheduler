package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.object.AbstractDao;
import by.epam.tc.conference.entity.Proposal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProposalDao extends AbstractDao<Long, Proposal> {

    private static final String UPDATE = "update proposal set p_title=?, p_description=?, p_s_id=?, p_u_id=?, " +
            "p_status=? where p_id=?";
    private static final String DELETE = "delete from proposal where p_id=?";
    private static final String SELECT = "select * from proposal where p_id=?";
    private static final String SELECT_ALL = "select * from proposal";
    private static final String SAVE = "insert into proposal (p_title, p_description, p_s_id, p_u_id, p_status) " +
            "values(?, ?, ?, ?, ?)";

    public ProposalDao(ResultHandler<Proposal> resultHandler, Executor<Long, Proposal> executor) {
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
    protected void configureUpdateStatement(Proposal entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getTitle());
        statement.setString(1, entity.getDescription());
        statement.setLong(2,entity.getSectionId());
        statement.setLong(3, entity.getParticipantId());
        String status = entity.getStatus().name();
        statement.setString(4, status);
        statement.setLong(5, entity.getId());
    }

    @Override
    protected void configureSelectByIdStatement(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    @Override
    protected void configureSaveStatement(Proposal entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getTitle());
        statement.setString(1, entity.getDescription());
        statement.setLong(2,entity.getSectionId());
        statement.setLong(3, entity.getParticipantId());
        String status = entity.getStatus().name();
        statement.setString(4, status);
    }

    @Override
    protected void configureDeleteStatement(Proposal entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());

    }
}

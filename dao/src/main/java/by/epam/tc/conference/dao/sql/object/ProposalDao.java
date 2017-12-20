package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.entity.Proposal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProposalDao extends AbstractDao<Proposal> {

    private static final String UPDATE = "update proposal set p_title=?, p_description=?, p_s_id=?, p_u_id=?, " +
            "p_status=? where p_id=?";
    private static final String DELETE = "delete from proposal where p_id=?";
    private static final String SELECT = "select * from proposal where p_id=?";
    private static final String SELECT_ALL = "select * from proposal";
    private static final String SAVE = "insert into proposal (p_title, p_description, p_s_id, p_u_id, p_status) " +
            "values(?, ?, ?, ?, ?)";

    public ProposalDao(ResultHandler<Proposal> resultHandler, Executor<Proposal> executor) {
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
    protected void configureSaveStatement(Proposal proposal, PreparedStatement statement) throws SQLException {
        statement.setString(0, proposal.getTitle());
        statement.setString(1, proposal.getDescription());
        statement.setLong(2,proposal.getSectionId());
        statement.setLong(3, proposal.getParticipantId());
        String status = proposal.getStatus().name();
        statement.setString(4, status);
    }

    @Override
    protected void configureUpdateStatement(Proposal proposal, PreparedStatement statement) throws SQLException {
        configureSaveStatement(proposal, statement);
        statement.setLong(5, proposal.getId());
    }
}

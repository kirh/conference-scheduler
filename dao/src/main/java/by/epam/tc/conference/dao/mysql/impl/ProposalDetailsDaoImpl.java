package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDetailsDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.dto.ProposalDetails;

import java.util.List;

public class ProposalDetailsDaoImpl implements ProposalDetailsDao {

    private static final String SELECT_DETAILS_BY_SECTION_ID_SQL = "select p_id, p_title, p_status, u_username, "
            + "s_topic, c_name from proposal join user on p_u_id=u_id join section on p_s_id = s_id"
            + " join conference on s_c_id = c_id where p_s_id=?";
    private static final String SELECT_DETAILS_BY_PARTICIPANT_ID_SQL = "select p_id, p_title, p_status, u_username, "
            + "s_topic, c_name from proposal join user on p_u_id=u_id "
            + "join section on p_s_id = s_id join conference on s_c_id = c_id where p_u_id=?";

    private final Executor<ProposalDetails> executor;

    public ProposalDetailsDaoImpl(Executor<ProposalDetails> executor) {
        this.executor = executor;
    }

    @Override
    public List<ProposalDetails> findProposalsByUserId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_DETAILS_BY_PARTICIPANT_ID_SQL, id);
    }

    @Override
    public List<ProposalDetails> findProposalsBySectionId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_DETAILS_BY_SECTION_ID_SQL, id);
    }
}

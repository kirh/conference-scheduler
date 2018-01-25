package by.epam.tc.conference.dao;

import by.epam.tc.conference.dto.ProposalDetails;

import java.util.List;

/**
 * Represents object to access persisted proposal details data
 */
public interface ProposalDetailsDao {

    /**
     * Return list of proposals created by specified user
     * @param id user identifier
     * @return list of proposals created by specified user
     * @throws DaoException error during data access occurs
     */
    List<ProposalDetails> findProposalsByUserId(Long id) throws DaoException;

    /**
     * Return list of proposals applied to specified section
     * @param id section identifier
     * @return list of proposals applied to specified section
     * @throws DaoException Return list of proposals created by specified user
     */
    List<ProposalDetails> findProposalsBySectionId(Long id) throws DaoException;

}

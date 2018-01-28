package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import java.util.List;

/**
 * Represent object to access persisted proposal data
 */
public interface ProposalDao extends GenericDao<Proposal> {

    /**
     * Returns list of proposals to specified conference section
     * @param id conference section identifier
     * @return list of proposals to specified conference section
     * @throws DaoException error during data access occurs
     */
    List<Proposal> findBySectionId(long id) throws DaoException;

    /**
     * Returns list of proposals created by specified participant
     * @param id participant identifier
     * @return list of proposals created by specified participant
     * @throws DaoException error during data access occurs
     */
    List<Proposal> findByUserId(long id) throws DaoException;

    /**
     * Update status of proposal with specified id to given status value
     * @param id proposal identifier
     * @param status to set up
     * @throws DaoException error during data access occurs
     */
    void updateStatus(long id, ProposalStatus status) throws DaoException;
}

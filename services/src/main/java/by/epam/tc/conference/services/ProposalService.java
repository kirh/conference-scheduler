package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains Base operations with proposal
 */
public interface ProposalService {

    /**
     * Saves proposal to underlying persistence data storage and supplies with id
     * @param proposal to save
     * @throws InvalidEntityException when invalid proposal given
     * @throws ServiceException when error during data access occurs
     */
    void createProposal(Proposal proposal) throws InvalidEntityException, ServiceException;

    /**
     * Removes proposal with specified id
     * @param id proposal to remove
     * @throws ServiceException when error during data access occurs
     */
    void deleteProposal(Long id) throws ServiceException;

    /**
     * Returns list of details for proposals related to section with specified id
     * @param id section identifier
     * @return list of proposal details
     * @throws ServiceException when error during data access occurs
     */
    List<ProposalDetails> findProposalsDetailsBySectionId(Long id) throws ServiceException;

    /**
     * Returns list of details for proposals created by participant with specified id
     * @param id participant identifier
     * @return list of proposal details
     * @throws ServiceException when error during data access occurs
     */
    List<ProposalDetails> findProposalsDetailsByParticipantId(Long id) throws ServiceException;

    /**
     * Returns proposal with specified id
     * @param id proposal to return
     * @return proposal
     * @throws EntityNotFoundException when proposal not found
     * @throws ServiceException when error during data access occurs
     */
    Proposal getProposal(Long id) throws EntityNotFoundException, ServiceException;

    /**
     * Changes proposal status to given value for proposal with specified id
     * @param id proposal identifier
     * @param status to set up for proposal
     * @throws ServiceException when error during data access occurs
     */
    void updateStatus(Long id, ProposalStatus status) throws ServiceException;

    /**
     * Updates proposal with the same id to given proposal state
     * @param proposal to update
     * @throws InvalidEntityException when invalid proposal given
     * @throws ServiceException when error during data access occurs
     */
    void updateProposal(Proposal proposal) throws InvalidEntityException, ServiceException;


}

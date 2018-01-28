package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;

/**
 * Contains Base operations with proposal
 */
public interface ProposalService {

    /**
     * Saves proposal to underlying persistence data storage and supplies with id
     *
     * @param proposal to save
     * @throws InvalidDataException when invalid proposal given
     * @throws ServiceException     when error during data access occurs
     */
    void createProposal(Proposal proposal) throws ServiceException;

    /**
     * Removes proposal with specified id
     *
     * @param proposalId proposal to remove
     * @throws NoAuthorityException when user has no authority to remove proposal
     * @throws ServiceException     when error during data access occurs
     */
    void deleteProposal(long proposalId, long userId) throws ServiceException;

    /**
     * Returns proposal with specified id
     *
     * @param proposalId proposal to return
     * @return proposal
     * @throws NotFoundException when proposal not found
     * @throws ServiceException  when error during data access occurs
     */
    Proposal getProposal(long proposalId) throws ServiceException;

    /**
     * Changes proposal status to given value for proposal with specified id
     *
     * @param proposalId proposal identifier
     * @param status     to set up for proposal
     * @throws NoAuthorityException when user has no authority to update status     *
     * @throws ServiceException     when error during data access occurs
     */
    void updateStatus(long proposalId, ProposalStatus status, long userId) throws
            ServiceException;

    /**
     * Updates proposal with the same id to given proposal state
     *
     * @param proposal to update
     * @throws InvalidDataException when invalid proposal given
     * @throws NoAuthorityException when user has no authority to update proposal
     * @throws ServiceException     when error during data access occurs
     */
    void updateProposal(Proposal proposal, long userId) throws
            ServiceException;


}

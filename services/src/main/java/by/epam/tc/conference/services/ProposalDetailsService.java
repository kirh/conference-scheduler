package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains Base operations with proposal details.
 */
public interface ProposalDetailsService {

    /**
     * Returns list of details for proposals related to section with specified id
     * @param sectionId section identifier
     * @return list of proposal details
     * @throws ServiceException when error during data access occurs
     */
    List<ProposalDetails> findProposalsDetailsBySectionId(long sectionId) throws ServiceException;

    /**
     * Returns list of details for proposals created by participant with specified id
     * @param participantId participant identifier
     * @return list of proposal details
     * @throws ServiceException when error during data access occurs
     */
    List<ProposalDetails> findProposalsDetailsByParticipantId(long participantId) throws ServiceException;
}

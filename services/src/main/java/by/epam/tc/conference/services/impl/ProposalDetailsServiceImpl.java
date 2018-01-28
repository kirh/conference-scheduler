package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDetailsDao;
import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.ProposalDetailsService;
import by.epam.tc.conference.services.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProposalDetailsServiceImpl implements ProposalDetailsService {

    private static final Logger logger = LogManager.getLogger(ProposalServiceImpl.class);
    private final ProposalDetailsDao proposalDetailsDao;

    public ProposalDetailsServiceImpl(ProposalDetailsDao proposalDetailsDao) {
        this.proposalDetailsDao = proposalDetailsDao;
    }

    @Override
    public List<ProposalDetails> findProposalsDetailsBySectionId(long sectionId) throws ServiceException {
        try {
            List<ProposalDetails> proposals = proposalDetailsDao.findProposalsBySectionId(sectionId);
            logger.debug("Found {} details for proposals in section with id={}", proposals.size(), sectionId);
            return proposals;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProposalDetails> findProposalsDetailsByParticipantId(long participantId) throws ServiceException {
        try {
            List<ProposalDetails> proposals = proposalDetailsDao.findProposalsByUserId(participantId);
            logger.debug("Found {} details for proposals with participantId={}", proposals.size(), participantId);
            return proposals;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

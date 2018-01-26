package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDao;
import by.epam.tc.conference.dao.ProposalDetailsDao;
import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ProposalServiceImpl implements ProposalService {

    private static final Logger logger = LogManager.getLogger(ProposalServiceImpl.class);
    private final ProposalDao proposalDao;
    private final ProposalDetailsDao proposalDetailsDao;
    private final Validator<Proposal> validator;

    public ProposalServiceImpl(ProposalDao proposalDao, ProposalDetailsDao proposalDetailsDao, Validator<Proposal> validator) {
        this.proposalDao = proposalDao;
        this.proposalDetailsDao = proposalDetailsDao;
        this.validator = validator;
    }

    @Override
    public void createProposal(Proposal proposal) throws ServiceException, InvalidEntityException {
        try {
            validateProposal(proposal);
            proposalDao.save(proposal);
            logger.info("Created proposal id={} , title={}", proposal.getId(), proposal.getTitle());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteProposal(Long id) throws ServiceException {
        try {
            proposalDao.deleteById(id);
            logger.info("Deleted proposal id={}", id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProposalDetails> findProposalsDetailsBySectionId(Long id) throws ServiceException {
        try {
            List<ProposalDetails> proposals = proposalDetailsDao.findProposalsBySectionId(id);
            logger.debug("Found {} details for proposals in section with id={}", proposals.size(), id);
            return proposals;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProposalDetails> findProposalsDetailsByParticipantId(Long id) throws ServiceException {
        try {
            List<ProposalDetails> proposals = proposalDetailsDao.findProposalsByUserId(id);
            logger.debug("Found {} details for proposals with participantId={}", proposals.size(), id);
            return proposals;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Proposal getProposal(Long id) throws ServiceException, EntityNotFoundException {
        try {
            Optional<Proposal> optionalProposal = proposalDao.findById(id);
            Proposal proposal = optionalProposal.orElseThrow(() ->
                    new EntityNotFoundException("There is no conference with id=" + id)
            );
            logger.info("conference with id={} returned");
            return proposal;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateStatus(Long id, ProposalStatus status) throws ServiceException {
        try {
            proposalDao.updateStatus(id, status);
            logger.info("Changed status for proposal id={} to {}", id, status);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateProposal(Proposal proposal) throws ServiceException, InvalidEntityException {
        try {
            validateProposal(proposal);
            proposalDao.update(proposal);
            logger.info("Updated proposal id={}", proposal.getId());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void validateProposal(Proposal proposal) throws InvalidEntityException {
        boolean isValid = validator.validate(proposal);
        if (!isValid) {
            throw new InvalidEntityException("Invalid proposal id=" + proposal.getId());
        }
    }

}

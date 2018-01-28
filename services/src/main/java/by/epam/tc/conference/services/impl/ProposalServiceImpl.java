package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDao;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ProposalServiceImpl implements ProposalService {

    private static final Logger logger = LogManager.getLogger(ProposalServiceImpl.class);
    private final ProposalDao proposalDao;
    private final UserDao userDao;
    private final Validator<Proposal> validator;

    public ProposalServiceImpl(ProposalDao proposalDao, UserDao userDao, Validator<Proposal> validator) {
        this.proposalDao = proposalDao;
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    public void createProposal(Proposal proposal) throws ServiceException {
        try {
            validateProposal(proposal);
            proposalDao.save(proposal);
            logger.info("Created proposal id={} , title={}", proposal.getId(), proposal.getTitle());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteProposal(long id, long userId) throws ServiceException {
        try {
            if (!hasAuthority(id, userId)) {
                throw new NoAuthorityException("Cannot delete proposal id=" + id + " No authority");
            }
            proposalDao.deleteById(id);
            logger.info("Deleted proposal id={}", id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Proposal getProposal(long proposalId) throws ServiceException {
        try {
            Optional<Proposal> optionalProposal = proposalDao.findById(proposalId);
            Proposal proposal = optionalProposal.orElseThrow(() ->
                    new NotFoundException("There is no conference with id=" + proposalId)
            );
            logger.info("conference with id={} returned");
            return proposal;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateStatus(long id, ProposalStatus status, long userId) throws ServiceException {
        try {
            if (!canChangeStatus(id, userId)) {
                throw new NoAuthorityException("Cannot change status for proposal id=" + id + " No authority");
            }
            proposalDao.updateStatus(id, status);
            logger.info("Changed status for proposal id={} to {}", id, status);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateProposal(Proposal proposal, long userId) throws ServiceException {
        try {
            validateProposal(proposal);
            Long proposalId = proposal.getId();
            if (!hasAuthority(proposalId, userId)) {
                throw new NoAuthorityException("Cannot update proposal id=" + proposalId + " No authority");
            }
            proposalDao.update(proposal);
            logger.info("Updated proposal id={}", proposal.getId());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * When proposal is invalid throws {@link InvalidDataException}
     * Proposal id == null is valid state
     * @param proposal to validate
     * @throws InvalidDataException when proposal invalid
     */
    private void validateProposal(Proposal proposal) throws InvalidDataException {
        boolean isValid = validator.validate(proposal);
        if (!isValid) {
            throw new InvalidDataException("Invalid proposal id=" + proposal.getId());
        }
    }

    private boolean canChangeStatus(long proposalId, long userId) throws DaoException {
        Optional<User> optionalAdministrator = userDao.findAdministratorByProposalId(proposalId);
        if (!optionalAdministrator.isPresent()) {
            return false;
        }
        User administrator = optionalAdministrator.get();
        Long administratorId = administrator.getId();
        return administratorId == userId;
    }

    private boolean hasAuthority(long proposalId, long userId) throws DaoException {
        Optional<Proposal> optionalProposal = proposalDao.findById(proposalId);
        if (!optionalProposal.isPresent()) {
            return false;
        }
        Proposal proposal = optionalProposal.get();
        Long participantId = proposal.getParticipantId();
        return userId == participantId;
    }
}

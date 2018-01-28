package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.ConferenceDao;
import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ConferenceService}
 * Contains base operation with conferences
 */
public class ConferenceServiceImpl implements ConferenceService {

    private static final Logger logger = LogManager.getLogger(ConferenceServiceImpl.class);
    private final ConferenceDao conferenceDao;
    private final Validator<Conference> validator;

    public ConferenceServiceImpl(ConferenceDao conferenceDao, Validator<Conference> validator) {
        this.conferenceDao = conferenceDao;
        this.validator = validator;
    }

    @Override
    public List<Conference> findConferencesByAdministratorId(long administratorId) throws ServiceException {
        try {
            List<Conference> conferences = conferenceDao.findConferencesByUserId(administratorId);
            logger.debug("Found {} conferences with administrator id = {}", conferences.size(), administratorId);
            return conferences;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void createConference(Conference conference) throws ServiceException {
        try {
            validateConference(conference);
            conferenceDao.save(conference);
            logger.info("Created conference id={} name={}", conference.getId(),  conference.getName());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateConference(Conference conference, long userId) throws ServiceException {
        try {
            validateConference(conference);
            Long conferenceId = conference.getId();
            if (!hasAuthority(conferenceId, userId)) {
                throw new NoAuthorityException("Failed to update conference id=" + conferenceId + " user id=" + userId + " " +
                        "has " +
                        "no authority");
            }
            conferenceDao.update(conference);
            logger.info("Updated conference id={} name={}", conference.getId(), conference.getName());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteConferenceById(long id, long userId) throws ServiceException {
        try {
            if (!hasAuthority(id, userId)) {
                throw new NoAuthorityException("Failed to delete conference id=" + id + " user id=" + userId + " has " +
                        "no authority");
            }
            conferenceDao.deleteById(id);
            logger.info("Deleted conference id={}", id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Conference getConference(long conferenceId) throws ServiceException {
        try {
            Optional<Conference> optionalConference = conferenceDao.findById(conferenceId);
            Conference conference = optionalConference.orElseThrow(() ->
                    new NotFoundException("There is no conference with id=" + conferenceId)
            );
            logger.debug("conference with id={} returned", conferenceId);
            return conference;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<Conference> getAllConferences() throws ServiceException {
        List<Conference> conferences = null;
        try {
            conferences = conferenceDao.findAll();
            logger.debug("Found {} conferences", conferences.size());
            return conferences;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * When conference is invalid throws {@link InvalidDataException}
     * Conference id == null is valid state
     * @param conference to validate
     * @throws InvalidDataException when conference invalid
     */
    private void validateConference(Conference conference) throws InvalidDataException {
        boolean isValidConference = validator.validate(conference);
        if (!isValidConference) {
            throw new InvalidDataException("Invalid conference " + conference);
        }
    }

    /**
     * Checks if user has authority to manage specified conference
     * @param conferenceId conference identifier
     * @param userId user identifier
     * @return true if user can manage conference
     * @throws DaoException when error during data access occurs
     */
    private boolean hasAuthority(long conferenceId, long userId) throws DaoException {
        Optional<Conference> optionalConference = conferenceDao.findById(conferenceId);
        if (!optionalConference.isPresent()) {
            return false;
        }
        Conference conference = optionalConference.get();
        Long administratorId = conference.getAdministratorId();
        return administratorId == userId;
    }
}

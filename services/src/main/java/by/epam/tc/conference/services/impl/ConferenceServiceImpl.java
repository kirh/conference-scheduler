package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.ConferenceDao;
import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ConferenceServiceImpl implements ConferenceService {

    private static final Logger logger = LogManager.getLogger(ConferenceServiceImpl.class);
    private  final ConferenceDao conferenceDao;

    public ConferenceServiceImpl(ConferenceDao conferenceDao) {
        this.conferenceDao = conferenceDao;
    }

    @Override
    public List<Conference> findConferencesByAdministratorId(Long administratorId) throws ServiceException {
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
            conferenceDao.save(conference);
            logger.info("Created conference id={} name={}", conference.getId(),  conference.getName());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateConference(Conference conference) throws ServiceException {
        try {
            conferenceDao.update(conference);
            logger.info("Updated conference id={} name={}", conference.getId(), conference.getName());
        } catch (DaoException e) {
            throw new ServiceException("Failed to update conference");
        }
    }

    @Override
    public void deleteConferenceById(Long id) throws ServiceException {
        try {
            conferenceDao.deleteById(id);
            logger.info("Deleted conference id={}", id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete conference " + e.getMessage(), e);
        }
    }

    @Override
    public Conference getConference(Long id) throws ServiceException {
        try {
            Optional<Conference> optionalConference = conferenceDao.findById(id);
            Conference conference = optionalConference.orElseThrow(() ->
                    new ServiceException("There is no conference with id=" + id)
            );
            logger.debug("Conference with id={} returned", id);
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
}

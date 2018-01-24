package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

public interface ConferenceService {
    List<Conference> findConferencesByAdministratorId(Long userId) throws ServiceException;

    void createConference(Conference conference) throws InvalidEntityException, ServiceException;

    void updateConference(Conference conference) throws InvalidEntityException, ServiceException;

    void deleteConferenceById(Long id) throws ServiceException;

    Conference getConference(Long id) throws EntityNotFoundException, ServiceException;

    List<Conference> getAllConferences() throws ServiceException;
}

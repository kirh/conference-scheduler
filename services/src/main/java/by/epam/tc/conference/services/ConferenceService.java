package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Conference;

import java.util.List;

public interface ConferenceService {
    List<Conference> findConferencesByAdministratorId(Long userId) throws ServiceException;

    void createConference(Conference conference) throws ServiceException;

    void updateConference(Conference conference) throws ServiceException;

    void deleteConferenceById(Long id) throws ServiceException;

    Conference getConference(Long id) throws ServiceException;

    List<Conference> getAllConferences() throws ServiceException;
}

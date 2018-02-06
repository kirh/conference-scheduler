package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains base operation with conferences.
 */
public interface ConferenceService {

    /**
     * Returns all conferences for administrator with specified id.
     * @param administratorId administrator identifier to search conferences
     * @return list of conferences created by administrator with given id
     * @throws ServiceException when error during data access occurs
     */
    List<Conference> findConferencesByAdministratorId(long administratorId) throws ServiceException;

    /**
     * Saves conference to underlying persistence data storage and supplies with id.
     * @param conference to save
     * @throws InvalidDataException when invalid conference given
     * @throws ServiceException when error during data access occurs
     */
    void createConference(Conference conference) throws ServiceException;

    /**
     * Updates conference with the same id to given conference property values.
     * @param conference to update
     * @param userId user who wants to update conference
     * @throws InvalidDataException when invalid conference given
     * @throws ServiceException when error during data access occurs
     */
    void updateConference(Conference conference, long userId) throws
            ServiceException;

    /**
     * Removes conference with specified id.
     * @param conferenceId conference to delete identifier
     * @param userId user who wants to delete conference
     * @throws ServiceException when error during data access occurs
     */
    void deleteConferenceById(long conferenceId, long userId) throws ServiceException;

    /**
     * Returns conference with specified id.
     * @param conferenceId conference to get identifier
     * @return conference with specified id
     * @throws NotFoundException when no conference with given id was found
     * @throws ServiceException when error during data access occurs
     */
    Conference getConference(long conferenceId) throws ServiceException;

    /**
     * Returns list of all upcoming conferences.
     * @return list of all upcoming conferences
     * @throws ServiceException when error during data access occurs
     */
    List<Conference> getAllUpComingConferences() throws ServiceException;
}

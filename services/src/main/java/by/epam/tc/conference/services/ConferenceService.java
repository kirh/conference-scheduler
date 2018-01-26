package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains base operation with conferences
 */
public interface ConferenceService {

    /**
     * Returns all conferences for administrator with specified id
     * @param administratorId administrator identifier to search conferences
     * @return list of conferences created by administrator with given id
     * @throws ServiceException when error during data access occurs
     */
    List<Conference> findConferencesByAdministratorId(Long administratorId) throws ServiceException;

    /**
     * Saves conference to underlying persistence data storage and supplies with id
     * @param conference to save
     * @throws InvalidEntityException when invalid conference given
     * @throws ServiceException when error during data access occurs
     */
    void createConference(Conference conference) throws InvalidEntityException, ServiceException;

    /**
     * Updates conference with the same id to given conference property values
     * @param conference to update
     * @throws InvalidEntityException when invalid conference given
     * @throws ServiceException when error during data access occurs
     */
    void updateConference(Conference conference) throws InvalidEntityException, ServiceException;

    /**
     * Removes conference with specified id
     * @param id conference to delete identifier
     * @throws ServiceException when error during data access occurs
     */
    void deleteConferenceById(Long id) throws ServiceException;

    /**
     * Returns conference with specified id
     * @param id conference to get identifier
     * @return conference with specified id
     * @throws EntityNotFoundException when no conference with given id was found
     * @throws ServiceException when error during data access occurs
     */
    Conference getConference(Long id) throws EntityNotFoundException, ServiceException;

    /**
     * Returns list of all upcoming conferences
     * @return list of all upcoming conferences
     * @throws ServiceException when error during data access occurs
     */
    List<Conference> getAllConferences() throws ServiceException;
}

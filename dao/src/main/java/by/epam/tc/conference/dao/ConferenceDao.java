package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Conference;

import java.util.List;

/**
 * Represent object to access persisted conference data
 */
public interface ConferenceDao extends GenericDao<Conference> {

    /**
     * Returns a List of conferences by administrator identifier
     * @param id is conference administrator identifier
     * @return conferences
     * @throws DaoException when error during data access occurs
     */
    List<Conference> findConferencesByUserId(long id) throws DaoException;

    /**
     * Returns list of all planned conferences from underlying persistence data storage
     *
     * @return list of all objects from underlying persistence data storage
     * @throws DaoException when access error occurs
     */
    List<Conference> findAllActual() throws DaoException;
}

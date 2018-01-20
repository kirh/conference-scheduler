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
     * @throws DaoException
     */

    List<Conference> findConferencesByUserId(Long id) throws DaoException;
}

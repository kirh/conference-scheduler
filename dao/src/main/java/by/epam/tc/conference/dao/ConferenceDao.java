package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Conference;

import java.util.List;

public interface ConferenceDao extends GenericDao<Conference> {

    List<Conference> findConferencesByUserId(Long id) throws DaoException;
}

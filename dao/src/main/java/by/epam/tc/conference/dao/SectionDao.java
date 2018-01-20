package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Section;

import java.util.List;

/**
 * Represent object to access persisted section data
 */
public interface SectionDao extends GenericDao<Section> {

    List<Section> findSectionsByConferenceId(Long id) throws DaoException;

}

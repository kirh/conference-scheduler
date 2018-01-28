package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Section;

import java.util.List;

/**
 * Represent object to access persisted section data
 */
public interface SectionDao extends GenericDao<Section> {

    /**
     * Finds all sections related to conference with specified id
     * @param id conference identifier
     * @return list of sections
     * @throws DaoException error during data access occurs
     */
    List<Section> findSectionsByConferenceId(long id) throws DaoException;

}

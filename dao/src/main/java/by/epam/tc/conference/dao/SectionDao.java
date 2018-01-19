package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Section;

import java.util.List;

public interface SectionDao extends GenericDao<Section> {

    List<Section> findSectionsByConferenceId(Long id) throws DaoException;

}

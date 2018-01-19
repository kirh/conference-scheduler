package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Section;

import java.util.List;

public interface SectionService {

    List<Section> findSectionsByConferenceId(Long id) throws ServiceException;

    void createSection(Section section) throws ServiceException;

    void deleteSection(Long id) throws ServiceException;

    void updateSection(Section section) throws ServiceException;

    Section getSection(Long id) throws ServiceException;
}

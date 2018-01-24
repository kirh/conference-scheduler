package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

public interface SectionService {

    List<Section> findSectionsByConferenceId(Long id) throws ServiceException;

    void createSection(Section section) throws InvalidEntityException, ServiceException;

    void deleteSection(Long id) throws ServiceException;

    void updateSection(Section section) throws InvalidEntityException, ServiceException;

    Section getSection(Long id) throws EntityNotFoundException, ServiceException;
}

package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains base operation with sections
 */
public interface SectionService {

    /**
     * Returns sections of specified conference
     * @param id conference identifier
     * @returnlist of conferences
     * @throws ServiceException when error during data access occurs
     */
    List<Section> findSectionsByConferenceId(Long id) throws ServiceException;

    /**
     * Saves section to underlying persistence data storage and supplies with id
     * @param section to save
     * @throws InvalidEntityException when invalid section given
     * @throws ServiceException when error during data access occurs
     */
    void createSection(Section section) throws InvalidEntityException, ServiceException;

    /**
     * Removes section with specified id
     * @param id section identifier
     * @throws ServiceException when error during data access occurs
     */
    void deleteSection(Long id) throws ServiceException;

    /**
     * Updates section with the same id to given section state
     * @param section to update
     * @throws InvalidEntityException when invalid section given
     * @throws ServiceException when error during data access occurs
     */
    void updateSection(Section section) throws InvalidEntityException, ServiceException;

    /**
     * Returns section with specified id
     * @param id section identifier
     * @return section
     * @throws EntityNotFoundException when section with specified id not found
     * @throws ServiceException when error during data access occurs
     */
    Section getSection(Long id) throws EntityNotFoundException, ServiceException;
}

package by.epam.tc.conference.services;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains base operation with sections.
 */
public interface SectionService {

    /**
     * Returns sections of specified conference
     *
     * @param id conference identifier
     * @return list of conferences
     * @throws ServiceException when error during data access occurs
     */
    List<Section> findSectionsByConferenceId(long id) throws ServiceException;

    /**
     * Saves section to underlying persistence data storage and supplies with id
     *
     * @param section to save
     * @throws InvalidDataException when invalid section given
     * @throws ServiceException     when error during data access occurs
     */
    void createSection(Section section) throws ServiceException;

    /**
     * Removes section with specified id
     *
     * @param sectionId     section identifier
     * @param userId identifier of user who wants to perform operation
     * @throws NoAuthorityException when user has no authority to delete this section
     * @throws ServiceException     when error during data access occurs
     */
    void deleteSection(long sectionId, long userId) throws ServiceException;

    /**
     * Updates section with the same id to given section state
     *
     * @param section to update
     * @param userId  identifier of user who wants to perform operation
     * @throws InvalidDataException when invalid section given
     * @throws NoAuthorityException when user has no authority to update this section
     * @throws ServiceException     when error during data access occurs
     */
    void updateSection(Section section, long userId) throws ServiceException;

    /**
     * Returns section with specified id
     *
     * @param sectionId section identifier
     * @return section
     * @throws NotFoundException when section with specified id not found
     * @throws ServiceException  when error during data access occurs
     */
    Section getSection(long sectionId) throws ServiceException;
}

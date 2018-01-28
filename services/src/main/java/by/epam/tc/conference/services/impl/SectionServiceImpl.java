package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.SectionDao;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SectionServiceImpl implements SectionService {

    private static final Logger logger = LogManager.getLogger(SectionServiceImpl.class);
    private final SectionDao sectionDao;
    private final UserDao userDao;
    private final Validator<Section> validator;

    public SectionServiceImpl(SectionDao sectionDao, UserDao userDao, Validator<Section> validator) {
        this.sectionDao = sectionDao;
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    public List<Section> findSectionsByConferenceId(long conferenceId) throws ServiceException {
        try {
            List<Section> sections = sectionDao.findSectionsByConferenceId(conferenceId);
            logger.debug("Found {} sections for conference with id={}", sections.size(), conferenceId);
            return sections;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void createSection(Section section) throws ServiceException {
        try {
            validateSection(section);
            sectionDao.save(section);
            logger.info("Created section id={} topic={}", section.getId(), section.getTopic());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteSection(long sectionId, long userId) throws ServiceException {
        try {
            if (!hasAuthority(sectionId, userId)) {
                throw new NoAuthorityException("User id=" + userId + " has no authority to delete section id=" + sectionId);
            }
            sectionDao.deleteById(sectionId);
            logger.info("Deleted section id={}", sectionId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateSection(Section section, long userId) throws ServiceException {
        try {
            validateSection(section);
            Long sectionId = section.getId();
            if (!hasAuthority(sectionId, userId)) {
                throw new NoAuthorityException("User id=" + userId + " has no authority to update section id=" + sectionId);
            }
            sectionDao.update(section);
            logger.info("Updated section id={} topic={}", section.getId(), section.getTopic());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Section getSection(long id) throws ServiceException {
        try {
            Optional<Section> optionalSection = sectionDao.findById(id);
            Section section = optionalSection.orElseThrow(() ->
                    new NotFoundException("Not found section with id={}"));
            logger.debug("Section id={} returned", id);
            return section;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * When section is invalid throws {@link InvalidDataException}
     * Proposal id == null is valid state
     * @param section to validate
     * @throws InvalidDataException when section invalid
     */
    private void validateSection(Section section) throws InvalidDataException {
        boolean isValid = validator.validate(section);
        if (!isValid) {
            throw new InvalidDataException("Invalid proposal id=" + section.getId());
        }
    }

    private boolean hasAuthority(long sectionId, long userId) throws DaoException {
        Optional<User> optionalAdministrator = userDao.findAdministratorBySectionId(sectionId);
        if (!optionalAdministrator.isPresent()) {
            return false;
        }
        User administrator = optionalAdministrator.get();
        long administratorId = administrator.getId();
        return administratorId == userId;
    }
}

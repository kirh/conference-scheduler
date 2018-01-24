package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.SectionDao;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SectionServiceImpl implements SectionService {

    private static final Logger logger = LogManager.getLogger(SectionServiceImpl.class);
    private final SectionDao sectionDao;

    public SectionServiceImpl(SectionDao sectionDao) {
        this.sectionDao = sectionDao;
    }

    @Override
    public List<Section> findSectionsByConferenceId(Long id) throws ServiceException {
        try {
            List<Section> sections = sectionDao.findSectionsByConferenceId(id);
            logger.debug("Found {} sections for conference with id={}", sections.size(), id);
            return sections;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void createSection(Section section) throws ServiceException {
        try {
            sectionDao.save(section);
            logger.info("Created section id={} topic={}", section.getId(), section.getTopic());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteSection(Long id) throws ServiceException {
        try {
            sectionDao.deleteById(id);
            logger.info("Deleted section id={}", id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateSection(Section section) throws ServiceException {
        try {
            sectionDao.update(section);
            logger.info("Updated section id={} topic={}", section.getId(), section.getTopic());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Section getSection(Long id) throws EntityNotFoundException, ServiceException {
        try {
            Optional<Section> optionalSection = sectionDao.findById(id);
            Section section = optionalSection.orElseThrow(() ->
                    new EntityNotFoundException("Not found section with id={}"));
            logger.debug("Section id={} returned", id);
            return section;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

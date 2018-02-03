package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.SectionDao;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SectionServiceImplTest {

    @Mock
    private SectionDao sectionDao;

    @Mock
    private Validator<Section> validator;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    public void shouldBeSectionsWhenConferenceIdGiven() throws DaoException, ServiceException {
        List<Section> sections = Collections.emptyList();
        when(sectionDao.findSectionsByConferenceId(1L)).thenReturn(sections);

        List<Section> actual = sectionService.findSectionsByConferenceId(1L);
        assertThat(actual, is(sameInstance(sections)));
    }

    @Test(expected = InvalidDataException.class)
    public void shouldBeExceptionOnCreateWhenInvalidSectionGiven() throws ServiceException {
        Section section = new Section();
        when(validator.validate(section)).thenReturn(false);

        sectionService.createSection(section);
    }

    @Test
    public void shouldSaveSectionOnCreateWhenValidSectionGiven() throws ServiceException, DaoException {
        Section section = new Section();
        when(validator.validate(section)).thenReturn(true);

        sectionService.createSection(section);

        verify(sectionDao).save(section);
    }

    @Test(expected = NoAuthorityException.class)
    public void shouldNotDeleteSectionOnDeleteWhenNotAdministrator() throws DaoException, ServiceException {
        User user = new User();
        user.setId(2L);
        when(userDao.findAdministratorBySectionId(1L)).thenReturn(Optional.of(user));

        sectionService.deleteSection(1L, 1L);
        verify(sectionDao, never()).deleteById(1L);
    }

    @Test
    public void shouldDeleteSectionOnDeleteWhenUserIsAdministrator() throws DaoException, ServiceException {
        User user = new User();
        user.setId(2L);
        when(userDao.findAdministratorBySectionId(1L)).thenReturn(Optional.of(user));

        sectionService.deleteSection(1L, 2L);

        verify(sectionDao).deleteById(1L);
    }

    @Test(expected = InvalidDataException.class)
    public void shouldNotUpdateSectionWhenSectionIsInvalid() throws ServiceException {
        Section section = new Section();
        when(validator.validate(section)).thenReturn(false);

        sectionService.updateSection(section, 1L);
    }

    @Test(expected = NoAuthorityException.class)
    public void shouldNotUpdateSectionOnUpdateWhenNotAdministrator() throws DaoException, ServiceException {
        User user = new User();
        user.setId(2L);
        when(userDao.findAdministratorBySectionId(1L)).thenReturn(Optional.of(user));
        Section section = new Section();
        section.setId(1L);
        when(validator.validate(section)).thenReturn(true);

        sectionService.updateSection(section, 1L);

        verify(sectionDao, never()).update(section);
    }

    @Test
    public void shouldUpdateSection() throws DaoException, ServiceException {
        User user = new User();
        user.setId(2L);
        when(userDao.findAdministratorBySectionId(1L)).thenReturn(Optional.of(user));
        Section section = new Section();
        section.setId(1L);
        when(validator.validate(section)).thenReturn(true);

        sectionService.updateSection(section, 2L);

        verify(sectionDao).update(section);
    }

    @Test
    public void shouldBeSectionWhenSectionIdGiven() throws ServiceException, DaoException {
        Section section = new Section();
        when(sectionDao.findById(1L)).thenReturn(Optional.of(section));

        Section actual = sectionService.getSection(1L);

        assertThat(actual, is(sameInstance(section)));
    }

    @Test(expected = NotFoundException.class)
    public void shouldBeExceptionWhenSectionWithGivenIdNotFound() throws DaoException, ServiceException {
        when(sectionDao.findById(1L)).thenReturn(Optional.empty());

        sectionService.getSection(1L);
    }
}
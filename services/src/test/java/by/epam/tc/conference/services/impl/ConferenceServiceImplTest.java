package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.ConferenceDao;
import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.junit.Before;
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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceServiceImplTest {

    @Mock
    private ConferenceDao conferenceDao;

    @Mock
    private Validator<Conference> validator;

    @InjectMocks
    private ConferenceServiceImpl conferenceService;

    private Conference conference;

    @Before
    public void setUp() throws Exception {
        conference = new Conference();
        conference.setId(1L);
    }

    @Test
    public void shouldBeConferencesWhenFindConferenceByAdministatorId() throws DaoException, ServiceException {
        List<Conference> conferences = Collections.emptyList();
        when(conferenceDao.findConferencesByUserId(1L)).thenReturn(conferences);

        List<Conference> actualConferences = conferenceService.findConferencesByAdministratorId(1L);

        verify(conferenceDao).findConferencesByUserId(1L);
        assertThat(actualConferences, is(sameInstance(conferences)));
    }

    @Test(expected = InvalidDataException.class)
    public void shouldBeExceptionOnCreateConferenceWhenInvalidConferenceGiven() throws ServiceException {
        when(validator.validate(conference)).thenReturn(false);

        conferenceService.createConference(conference);
    }

    @Test
    public void shouldSaveConferenceOnCreateWhenValid() throws ServiceException, DaoException {
        when(validator.validate(conference)).thenReturn(true);

        conferenceService.createConference(conference);

        verify(conferenceDao).save(same(conference));
    }

    @Test(expected = InvalidDataException.class)
    public void shouldBeExceptionOnUpdateWhenInvalidConferenceGiven() throws ServiceException {

        when(validator.validate(conference)).thenReturn(false);

        conferenceService.updateConference(conference, 1L);
    }

    @Test(expected = NoAuthorityException.class)
    public void shouldBeExceptionOnUpdateWhenUserNotConferenceAdministrator() throws ServiceException, DaoException {
        conference.setId(1L);
        conference.setAdministratorId(2L);

        when(conferenceDao.findById(1L)).thenReturn(Optional.of(conference));
        when(validator.validate(conference)).thenReturn(true);

        conferenceService.updateConference(conference, 3L);
    }

    @Test
    public void shouldDeleteConfererenceWithSpecifiedIdWhenUserIsAdmin() throws ServiceException, DaoException {
        conference.setId(1L);
        conference.setAdministratorId(2L);

        when(conferenceDao.findById(1L)).thenReturn(Optional.of(conference));

        conferenceService.deleteConferenceById(1L, 2L);

        verify(conferenceDao).deleteById(1L);
    }

    @Test(expected = NoAuthorityException.class)
    public void shouldBeExceptionOnDeleteWhenUserIsNotAdmin() throws DaoException, ServiceException {
        conference.setId(1L);
        conference.setAdministratorId(2L);

        when(conferenceDao.findById(1L)).thenReturn(Optional.of(conference));

        conferenceService.deleteConferenceById(1L, 3L);
    }

    @Test
    public void shouldBeConferenceWhenGetConference() throws DaoException, ServiceException {
        when(conferenceDao.findById(1L)).thenReturn(Optional.of(conference));

        Conference actual = conferenceService.getConference(1L);

        assertThat(actual, is(sameInstance(conference)));
    }

    @Test(expected = NotFoundException.class)
    public void shouldBeExceptionOnGetConferenceWhenNotFound() throws ServiceException, DaoException {
        when(conferenceDao.findById(1L)).thenReturn(Optional.empty());

        conferenceService.getConference(1L);
    }

    @Test
    public void shouldBeConferencesWhenGetAllConferences() throws ServiceException, DaoException {
        List<Conference> conferences = Collections.emptyList();
        when(conferenceDao.findAll()).thenReturn(conferences);

        List<Conference> actual = conferenceService.getAllConferences();

        assertThat(actual, is(sameInstance(conferences)));
    }
}
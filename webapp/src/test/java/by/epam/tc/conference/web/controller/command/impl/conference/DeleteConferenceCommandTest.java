package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteConferenceCommandTest {

    private static final long CURRENT_USER_ID = 1L;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ConferenceService conferenceService;

    @InjectMocks
    private DeleteConferenceCommand command;

    @Before
    public void setUp() throws Exception {
        UserPrincipal admin = new UserPrincipal(CURRENT_USER_ID, "admin", true);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(admin);
        when(request.getParameter("id")).thenReturn("1");
    }

    @Test
    public void shouldDeleteConferenceAndRedirectToDashboardWhenUserCreatedIt() throws ServiceException {
        when(request.getParameter("id")).thenReturn("1");
        Conference conference = new Conference();
        conference.setAdministratorId(CURRENT_USER_ID);
        final Long conferenceId = 1L;
        when(conferenceService.getConference(conferenceId)).thenReturn(conference);

        String view = command.execute(request, response);

        verify(conferenceService).deleteConferenceById(conferenceId);
        assertThat(view, is("redirect:/admin-dashboard"));
    }

    @Test
    public void shouldForbidRequestWhenUserIsNotAdministratorOfThisConference() throws ServiceException {
        when(request.getParameter("id")).thenReturn("1");
        Conference conference = new Conference();
        final Long notCurrentUserId = CURRENT_USER_ID + 1;
        conference.setAdministratorId(notCurrentUserId);
        when(conferenceService.getConference(1L)).thenReturn(conference);

        String view = command.execute(request, response);

        CommandTestHelper.assertThatRequestIsForbidden(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenServiceExceptionOccurs() throws ServiceException {
        when(request.getParameter("id")).thenReturn("1");
        when(conferenceService.getConference(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }

    @Test
    public void shouldBeBadRequestWhenInvalidId() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }
}
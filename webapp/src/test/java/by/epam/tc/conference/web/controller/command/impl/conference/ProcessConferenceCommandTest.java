package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
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
public class ProcessConferenceCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ConferenceService conferenceService;

    @Mock
    private Builder<Conference> builder;

    @InjectMocks
    private ProcessConferenceCommand command;

    @Test
    public void shouldCreateNewConferenceWhenConferenceIdNotSpecified() throws ServiceException, InvalidEntityException {
        Conference conference = new Conference();
        when(builder.build(request)).thenReturn(conference);

        command.execute(request, response);

        verify(conferenceService).createConference(same(conference));
    }

    @Test
    public void shouldUpdateConferenceWhenConferenceIdGiven() throws ServiceException, EntityNotFoundException, InvalidEntityException {
        Conference conference = new Conference();
        conference.setId(1L);
        when(builder.build(request)).thenReturn(conference);
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(getUser()).thenReturn(admin);
        Conference persistedConference = new Conference();
        persistedConference.setAdministratorId(1L);
        when(conferenceService.getConference(any())).thenReturn(persistedConference);

        command.execute(request, response);

        verify(conferenceService).updateConference(same(conference));
    }

    private Object getUser() {
        return request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL);
    }

    @Test
    public void shouldRedirectToDashboardWhenProcessedSuccessful() {
        when(builder.build(request)).thenReturn(new Conference());

        String view = command.execute(request, response);

        assertThat(view, is("redirect:/admin-dashboard"));
    }

    @Test
    public void shouldBeInternalErrorWhenServiceExceptionOccurs() throws ServiceException, InvalidEntityException {
        when(builder.build(request)).thenReturn(new Conference());
        doThrow(new ServiceException()).when(conferenceService).createConference(any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateConferenceCommandTest {


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ConferenceService conferenceService;

    @InjectMocks
    private UpdateConferenceCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
    }

    @Test
    public void shouldSpecifyConferenceAndReturnFormViewWhenSuccessful() {

        String view = command.execute(request, response);

        verify(request).setAttribute(eq("conference"), any(Conference.class));
        assertThat(view, is("conference-form"));
    }

    @Test
    public void shouldBeBadRequestWhenInvalidIdGiven() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBePageNotFoundWhenConferenceWithGivenIdNotExists() throws ServiceException {
        when(conferenceService.getConference(any())).thenThrow(new EntityNotFoundException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatPageNotFound(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenErrorUccurs() throws ServiceException {
        when(conferenceService.getConference(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
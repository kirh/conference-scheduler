package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.SectionService;
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
public class ShowConferenceCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ConferenceService conferenceService;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private SectionService sectionService;

    @InjectMocks
    private ShowConferenceCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
    }

    @Test
    public void shouldSpecifyConferenceAndSectionsWhenSuccessful() {

        String view = command.execute(request, response);

        verify(request).setAttribute(eq("conference"), any(Conference.class));
        verify(request).setAttribute(eq("sections"), anyList());
    }

    @Test
    public void shouldReturnConferenceViewWhenSuccessful() {

        String view = command.execute(request, response);

        assertThat(view, is("conference"));
    }

    @Test
    public void shouldBeBadRequestWhenInvalidId() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenServiceExceptionOccurs() throws ServiceException {
        when(conferenceService.getConference(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }

    @Test
    public void shouldBePageNotFoundWhenNoConferenceWithGivenId() throws ServiceException {
        when(conferenceService.getConference(any())).thenThrow(new EntityNotFoundException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatPageNotFound(request, response, view);
    }
}
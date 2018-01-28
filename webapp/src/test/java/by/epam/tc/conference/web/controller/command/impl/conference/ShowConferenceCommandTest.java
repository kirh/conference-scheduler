package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
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
    public void shouldSpecifyConferenceAndSectionsWhenSuccessful() throws CommandException {

        String view = command.execute(request, response);

        verify(request).setAttribute(eq("conference"), any(Conference.class));
        verify(request).setAttribute(eq("sections"), anyList());
    }

    @Test
    public void shouldReturnConferenceViewWhenSuccessful() throws CommandException {

        String view = command.execute(request, response);

        assertThat(view, is("conference"));
    }
}
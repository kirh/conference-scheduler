package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
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
public class ShowAllConferencesCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ConferenceService conferenceService;

    @InjectMocks
    private ShowAllConferencesCommand command;

    @Test
    public void shouldSpecifyConferencesAttribute() {
        command.execute(request, response);

        verify(request).setAttribute(eq("conferences"), anyList());
    }

    @Test
    public void shouldReturnConferencesViewWhenNoErrors() {
        String view = command.execute(request, response);

        assertThat(view, is("conferences"));
    }

    @Test
    public void shouldBeInternalErrorWhenServiceExceptionOccurs() throws ServiceException {
        when(conferenceService.getAllConferences()).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
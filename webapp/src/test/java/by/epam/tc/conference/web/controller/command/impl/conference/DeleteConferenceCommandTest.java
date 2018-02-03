package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    }

    @Test
    public void shouldDeleteConferenceWithCurrentUser() throws CommandException, ServiceException {
        when(request.getParameter("id")).thenReturn("1");

        command.execute(request, response);

        verify(conferenceService).deleteConferenceById(1L, CURRENT_USER_ID);
    }

    @Test
    public void shouldBeEmptyViewWhenDeleted() throws CommandException {
        when(request.getParameter("id")).thenReturn("1");

        String view = command.execute(request, response);

        assertThat(view, is(emptyString()));
    }
}
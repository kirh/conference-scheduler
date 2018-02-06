package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.SessionAttribute;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminDashboardCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ConferenceService conferenceService;

    @InjectMocks
    private AdminDashboardCommand command;

    @Before
    public void setUp() throws Exception {
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(admin);
    }

    @Test
    public void shouldSpecifyConferencesWhenExecute() throws CommandException, ServiceException {
        List<Conference> conferences = new ArrayList<>();
        when(conferenceService.findConferencesByAdministratorId(1L)).thenReturn(conferences);

        command.execute(request, response);

        verify(request).setAttribute(eq("conferences"), same(conferences));
    }

    @Test
    public void shouldReturnAdminDashboardViewWhenExecute() throws CommandException {

        String view = command.execute(request, response);

        assertThat(view, is("admin-dashboard"));
    }
}
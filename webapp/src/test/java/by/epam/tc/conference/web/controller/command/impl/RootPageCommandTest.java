package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.SessionAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RootPageCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private RootPageCommand command = new RootPageCommand();

    @Test
    public void shouldForwardToAdminDashboardWhenUserIsAdmin() {
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(admin);

        String view = command.execute(request, response);

        assertThat(view, is("forward:/admin-dashboard"));
    }

    @Test
    public void shouldForwardToParticipantDashboardWhenUserIsParticipant() {
        UserPrincipal participant = new UserPrincipal(1L, "participant", false);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(participant);

        String view = command.execute(request, response);

        assertThat(view, is("forward:/user-dashboard"));
    }
}
package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
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
public class ProcessRegisterCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService userService;

    @Mock
    private Builder<User> builder;

    @InjectMocks
    private ProcessRegisterCommand command;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldAttemptToRegisterWhenExecute() throws ServiceException, NullPointerException {
        User user = new User();
        when(builder.build(request)).thenReturn(user);
        UserPrincipal principal = new UserPrincipal(1L, "test", false);
        when(userService.registerUser(any())).thenReturn(principal);

        command.execute(request, response);

        verify(userService).registerUser(same(user));
    }

    @Test
    public void shouldRedirectToAdminDashboardWhenUserIsAdmin() throws ServiceException {
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(userService.registerUser(any())).thenReturn(admin);

        String view = command.execute(request, response);

        assertThat(view, is("redirect:/admin-dashboard"));
    }

    @Test
    public void shouldRedirectToParticipantDashboardWhenUserIsParticipant() throws ServiceException {
        UserPrincipal participant = new UserPrincipal(1L, "participant", false);
        when(userService.registerUser(any())).thenReturn(participant);

        String view = command.execute(request, response);


        assertThat(view, is("redirect:/user-dashboard"));
    }

    @Test
    public void shouldSpecifyErrorAndRedirectToRegisterPageWhenUserAlreadyExists() throws ServiceException {
        when(userService.registerUser(any())).thenThrow(new AlreadyExistsException());

        String view = command.execute(request, response);

        verify(request).setAttribute("error", "register.error.already-exists");
        assertThat(view, is("redirect:/register"));
    }

    @Test
    public void shouldBeInternalErrorWhenServiceExceptionOccurs() throws ServiceException {
        when(userService.registerUser(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
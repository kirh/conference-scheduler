package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.web.controller.SessionAttribute;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProcessLoginCommandTest {

    private static final String NOT_BLANK_PASSWORD = "notBlankPassword";
    private static final String NOT_BLANK_USERNAME = "notBlankUsername";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProcessLoginCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("username")).thenReturn(NOT_BLANK_USERNAME);
        when(request.getParameter("password")).thenReturn(NOT_BLANK_PASSWORD);
    }

    @Test
    public void shouldAttemptToLoginWhenUsernameAndPasswordNotBlank() throws Exception {
        UserPrincipal user = new UserPrincipal();
        when(userService.authenticateUser(any(), any())).thenReturn(user);

        command.execute(request, response);

        verify(userService).authenticateUser(NOT_BLANK_USERNAME, NOT_BLANK_PASSWORD);
    }

    @Test
    public void shouldSpecifyUserPrincipalSessionAttributeWhenLoggedInSuccessful() throws Exception {
        UserPrincipal user = new UserPrincipal();
        when(userService.authenticateUser(any(), any())).thenReturn(user);

        command.execute(request, response);

        verify(request.getSession()).setAttribute(eq(SessionAttribute.USER_PRINCIPAL), same(user));
    }

    @Test
    public void shouldRedirectToRootPageWhenProcessedSuccessful() throws Exception {
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(userService.authenticateUser(any(), any())).thenReturn(admin);

        String view = command.execute(request, response);

        assertThat(view, is("redirect:/"));
    }


}
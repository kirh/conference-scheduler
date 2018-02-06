package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.SessionAttribute;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class CommandTestHelper {

    public static HttpServletRequest getMockedAdminRequest() {
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class, Mockito.RETURNS_DEEP_STUBS);
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(mockedRequest.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(admin);
        return mockedRequest;
    }

    public static HttpServletRequest getMockedUserRequest() {
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class, Mockito.RETURNS_DEEP_STUBS);
        UserPrincipal user = new UserPrincipal(1L, "user", false);
        when(mockedRequest.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);
        return mockedRequest;
    }

    public static void assertThatRequestIsForbidden(HttpServletRequest mockedRequest, HttpServletResponse
            mockedResponse, String view) {
        verify(mockedRequest).setAttribute(eq("error"), argThat(not(emptyString())));
        verify(mockedResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
        assertThat(view, is("error"));
    }

    public static void assertThatPageNotFound(HttpServletRequest mockedRequest, HttpServletResponse
            mockedResponse, String view) {
        verify(mockedRequest).setAttribute(eq("error"), argThat(not(emptyString())));
        verify(mockedResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertThat(view, is("error"));
    }

    public static void assertThatInternalError(HttpServletRequest mockedRequest, HttpServletResponse
            mockedResponse, String view) {
        verify(mockedRequest).setAttribute(eq("error"), argThat(not(emptyString())));
        verify(mockedResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertThat(view, is("error"));
    }

    public static void assertThatBadRequest(HttpServletRequest mockedRequest, HttpServletResponse
            mockedResponse, String view) {
        verify(mockedRequest).setAttribute(eq("error"), argThat(not(emptyString())));
        verify(mockedResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertThat(view, is("error"));
    }
}

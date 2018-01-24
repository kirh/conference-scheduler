package by.epam.tc.conference.web.controller.command.impl;

import org.junit.Before;
import org.junit.Test;
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
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class AbstractCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);


    private AbstractCommand command;

    @Before
    public void setUp() {
        command = mock(AbstractCommand.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void shouldSpecifyErrorMessageKeyAndStatusWhenForbidRequest() {
        String view = command.forbidRequest(request, response);

        verify(request).setAttribute(eq("error"), argThat(is(not(emptyString()))));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        assertThat(view, is("error"));
    }

    @Test
    public void shouldSpecifyErrorMessageKeyAndStatusWhenBadRequest() {
        String view = command.processBadRequest(request, response);

        verify(request).setAttribute(eq("error"), argThat(is(not(emptyString()))));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertThat(view, is("error"));
    }

    @Test
    public void shouldSpecifyErrorMessageKeyAndStatusWhenInternalError() {
        String view = command.processInternalError(request, response);

        verify(request).setAttribute(eq("error"),  argThat(is(not(emptyString()))));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertThat(view, is("error"));
    }

    @Test
    public void shouldSpecifyErrorMessageKeyAndStatusWhenPageNotFound() {
        String view = command.processPageNotFound(request, response);

        verify(request).setAttribute(eq("error"), argThat(is(not(emptyString()))));
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertThat(view, is("error"));
    }
}
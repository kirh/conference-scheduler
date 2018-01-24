package by.epam.tc.conference.web.controller.command.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UnknownCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private UnknownCommand command = new UnknownCommand();

    @Test
    public void shouldBePageNotFoundWhenExecute() {
        String view = command.execute(request, response);

        CommandTestHelper.assertThatPageNotFound(request, response, view);
    }
}
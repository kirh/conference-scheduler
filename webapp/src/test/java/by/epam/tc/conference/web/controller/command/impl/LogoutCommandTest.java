package by.epam.tc.conference.web.controller.command.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private LogoutCommand command = new LogoutCommand();


    @Test
    public void shouldRedirectToLoginWhenExecute() {
        String query = command.execute(request, response);
        assertThat(query, is("redirect:/login"));
    }

    @Test
    public void shouldInvalidateSessionWhenExecute() {
        command.execute(request, response);

        verify(request.getSession()).invalidate();
    }
}
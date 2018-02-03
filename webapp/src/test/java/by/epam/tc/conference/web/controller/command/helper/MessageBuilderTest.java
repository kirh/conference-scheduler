package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.controller.SessionAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageBuilderTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    private MessageBuilder builder = new MessageBuilder();

    @Test
    public void shouldBuildMessageFromRequestParams() {
        UserPrincipal user = new UserPrincipal();
        user.setId(1L);

        when(getUser()).thenReturn(user);
        when(request.getParameter("text")).thenReturn("some text");
        when(request.getParameter("questionId")).thenReturn("1");

        Message expected = new Message("some text", 1L, 1L);
        Message actual = builder.build(request);

        assertThat(actual, is(expected));
    }

    private Object getUser() {
        return request.getSession()
                .getAttribute(SessionAttribute.USER_PRINCIPAL);
    }
}
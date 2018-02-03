package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.controller.SessionAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceBuilderTest {

    private final ConferenceBuilder builder = new ConferenceBuilder();

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Test
    public void shouldBuildConferenceWhenRequestWithParamsGiven() {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("joker");
        when(request.getParameter("description")).thenReturn("conf");
        when(request.getParameter("address")).thenReturn("Moscow");
        when(request.getParameter("datetime")).thenReturn("2017-11-11");
        UserPrincipal user = new UserPrincipal();
        user.setId(2L);
        when(getUser()).thenReturn(user);

        Conference expected = new Conference(1L, "joker", "conf", "Moscow",
                Date.valueOf("2017-11-11"), 2L);

        Conference result = builder.build(request);

        assertEquals(expected, result);
    }

    private Object getUser() {
        return request.getSession()
                .getAttribute(SessionAttribute.USER_PRINCIPAL);
    }
}
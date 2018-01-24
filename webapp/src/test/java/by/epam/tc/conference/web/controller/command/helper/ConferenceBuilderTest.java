package by.epam.tc.conference.web.controller.command.helper;

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

    private static final String ID = "1";
    private static final String NAME = "joker";
    private static final String DESCRIPTION = "conf";
    private static final String ADDRESS = "Moscow";
    private static final String DATE = "2017-11-11";
    private static final Long ADMIN_ID = 2L;
    private final ConferenceBuilder builder = new ConferenceBuilder();

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Test
    public void shouldBuildConferenceWhenRequestWithParamsGiven() {
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("description")).thenReturn(DESCRIPTION);
        when(request.getParameter("address")).thenReturn(ADDRESS);
        when(request.getParameter("datetime")).thenReturn(DATE);
        UserPrincipal user = new UserPrincipal();
        user.setId(ADMIN_ID);
        when(getUser()).thenReturn(user);

        Conference expected = new Conference(1L, NAME, DESCRIPTION, ADDRESS,
                Date.valueOf(DATE), ADMIN_ID);

        Conference result = builder.build(request);

        assertEquals(expected, result);
    }

    private Object getUser() {
        return request.getSession()
                .getAttribute(SessionAttribute.USER_PRINCIPAL);
    }
}
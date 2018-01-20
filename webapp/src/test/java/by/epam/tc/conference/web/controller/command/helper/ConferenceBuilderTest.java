package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Conference;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConferenceBuilderTest {

    private static final String ID = "1";
    private static final String NAME = "joker";
    private static final String DESCRIPTION = "conf";
    private static final String ADDRESS = "Moscow";
    private static final String DATE = "2017-11-11";
    private static final Long ADMIN_ID = 1L;

    private final ConferenceBuilder builder = new ConferenceBuilder();


    @Test
    @Ignore
    public void shouldBuildConferenceWhenRequestGiven() {
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        when(mockedRequest.getParameter("id")).thenReturn(ID);
        when(mockedRequest.getParameter("name")).thenReturn(NAME);
        when(mockedRequest.getParameter("description")).thenReturn(DESCRIPTION);
        when(mockedRequest.getParameter("address")).thenReturn(ADDRESS);
        when(mockedRequest.getParameter("datetime")).thenReturn(DATE);
        when(builder.getUserId(any())).thenReturn(1L);

        Conference expected = new Conference(1L, NAME, DESCRIPTION, ADDRESS,
                Date.valueOf(DATE), 1L);
        Conference result = builder.build(mockedRequest);

        assertEquals(expected, result);
    }
}
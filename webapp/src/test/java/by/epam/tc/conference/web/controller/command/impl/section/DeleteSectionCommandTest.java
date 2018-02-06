package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteSectionCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    private DeleteSectionCommand command;

    @Before
    public void setUp() throws Exception {
        UserPrincipal user = new UserPrincipal(1L, "participant", false);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);
    }

    @Test
    public void shouldDeleteSectionWithGivenId() throws ServiceException, CommandException {
        when(request.getParameter("id")).thenReturn("1");

        command.execute(request, response);

        verify(sectionService).deleteSection(1L,1L);
    }

    @Test
    public void shouldBeEmptyViewAfterDelete() throws CommandException {
        when(request.getParameter("id")).thenReturn("1");

        String view = command.execute(request, response);

        assertThat(view, is(emptyString()));
    }
}
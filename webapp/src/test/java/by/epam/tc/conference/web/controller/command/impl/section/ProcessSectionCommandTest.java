package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.bulder.Builder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcessSectionCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SectionService sectionService;

    @Mock
    private Builder<Section> builder;

    @InjectMocks
    private ProcessSectionCommand command;

    @Test
    public void shouldCreateSectionWhenNoIdGiven() throws ServiceException, CommandException {
        Section section = new Section();
        when(builder.build(request)).thenReturn(section);

        command.execute(request, response);

        verify(sectionService).createSection(section);
    }

    @Test
    public void shouldUpdateSectionWhenIdGiven() throws ServiceException, CommandException {
        Section section = new Section();
        section.setId(1L);
        when(builder.build(request)).thenReturn(section);
        UserPrincipal user = new UserPrincipal();
        user.setId(1L);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);

        command.execute(request, response);

        verify(sectionService).updateSection(section, 1L);
    }

    @Test
    public void shouldRedirectToConferencePageWhenSuccessful() throws CommandException {
        Section section = new Section();
        section.setConferenceId(1L);
        when(builder.build(request)).thenReturn(section);

        String view = command.execute(request, response);

        assertThat(view, is("redirect:/conference?action=show&id=1"));
    }
}
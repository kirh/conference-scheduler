package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateSectionCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    private UpdateSectionCommand command;

    @Test
    public void shouldSpecifySectionAttributeForGivenId() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        Section section = new Section();
        section.setId(1L);
        when(sectionService.getSection(1L)).thenReturn(section);
        command.execute(request, response);

        verify(request).setAttribute("section", section);
    }

    @Test
    public void shouldReturnSectionFormViewWhenSuccessful() throws CommandException {
        when(request.getParameter("id")).thenReturn("1");

        String view = command.execute(request, response);

        assertThat(view, is("section-form"));
    }
}
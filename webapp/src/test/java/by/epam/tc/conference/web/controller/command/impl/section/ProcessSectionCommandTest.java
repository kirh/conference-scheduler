package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcessSectionCommandTest {

    @Mock
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
    public void shouldCreateSectionWhenNoIdGiven() throws ServiceException, InvalidEntityException {
        Section section = new Section();
        when(builder.build(request)).thenReturn(section);

        command.execute(request, response);

        verify(sectionService).createSection(section);
    }

    @Test
    public void shouldUpdateSectionWhenIdGiven() throws ServiceException, InvalidEntityException {
        Section section = new Section();
        section.setId(1L);
        when(builder.build(request)).thenReturn(section);

        command.execute(request, response);

        verify(sectionService).updateSection(section);
    }

    @Test
    public void shouldRedirectToConferencePageWhenSuccessful() {
        Section section = new Section();
        section.setConferenceId(1L);
        when(builder.build(request)).thenReturn(section);

        String view = command.execute(request, response);

        assertThat(view, is("redirect:/conference?action=show&id=1"));
    }
}
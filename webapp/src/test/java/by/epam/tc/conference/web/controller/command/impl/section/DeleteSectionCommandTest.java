package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteSectionCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    DeleteSectionCommand command;

    @Test
    public void shouldBeBadRequestWhenInvalidIdGiven() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldDeleteSectionWithGivenId() throws ServiceException {
        when(request.getParameter("id")).thenReturn("1");

        command.execute(request, response);

        verify(sectionService).deleteSection(1L);
    }

    @Test
    public void shouldRedirectToPreviousPageAfterDelete() {
        when(request.getParameter("id")).thenReturn("1");

        String view = command.execute(request, response);

        assertThat(view, is("redirect:"));
    }
}
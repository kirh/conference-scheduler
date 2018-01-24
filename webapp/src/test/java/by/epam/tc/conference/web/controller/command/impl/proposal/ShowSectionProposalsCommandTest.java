package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowSectionProposalsCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProposalService proposalService;

    @InjectMocks
    private ShowSectionProposalsCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("sectionId")).thenReturn("1");
    }

    @Test
    public void shouldBeBadRequestWhenInvalidSectionId() {
        when(request.getParameter("sectionId")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldSpecifyProposalsAndReturnSectionViewWhenValidId() {
        String view = command.execute(request, response);

        verify(request).setAttribute(eq("proposals"), anyList());
        assertThat(view, is("section"));
    }

    @Test
    public void shouldBeInternalExceptionWhenErrorOccurs() throws ServiceException {
        when(proposalService.findProposalsDetailsBySectionId(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
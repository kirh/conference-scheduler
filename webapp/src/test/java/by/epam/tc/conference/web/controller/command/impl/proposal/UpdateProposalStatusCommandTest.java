package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
import org.junit.Before;
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
public class UpdateProposalStatusCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProposalService proposalService;

    @InjectMocks
    private UpdateProposalStatusCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("status")).thenReturn("pending");
    }

    @Test
    public void shouldBeBadRequestWhenInvalidIdGiven() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBeBadRequestWhenInvalidStatusGiven() {
        when(request.getParameter("status")).thenReturn("invalid status");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldUpdateStatusWhenValidDataGiven() throws ServiceException {
        command.execute(request, response);

        verify(proposalService).updateStatus(1L, ProposalStatus.PENDING);
    }

    @Test
    public void shouldRedirectToProposalAfterUpdate() {
        String view = command.execute(request, response);

        assertThat(view, is("redirect:/proposal?action=show&id=1"));
    }

    @Test
    public void shouldBeInternalErrorWhenErrorOccurs() throws ServiceException {
        doThrow(new ServiceException()).when(proposalService).updateStatus(any(), any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
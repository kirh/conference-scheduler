package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowProposalCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ProposalService proposalService;

    @InjectMocks
    private ShowProposalCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
    }

    @Test
    public void shouldSpecifyProposalWhenValidIdGiven() {
        when(request.getParameter("id")).thenReturn("1");

        command.execute(request, response);

        verify(request).setAttribute(eq("proposal"), any(Proposal.class));
    }

    @Test
    public void shouldReturnProposalViewWhenSuccesful() {
        String view = command.execute(request, response);

        assertThat(view, is("proposal"));
    }

    @Test
    public void shouldBeBadRequestWhenInvalidIdGiven() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBePageNotFoundWhenNoProposalWithSpecifiedIdExists() throws ServiceException {
        when(proposalService.getProposal(any())).thenThrow(new EntityNotFoundException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatPageNotFound(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenErrorOccurs() throws ServiceException {
        when(proposalService.getProposal(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
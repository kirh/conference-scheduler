package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
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
public class DeleteProposalCommandTest {

    private static final long CURRENT_USER_ID = 1L;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProposalService proposalService;

    @InjectMocks
    private DeleteProposalCommand command;

    @Before
    public void setUp() throws Exception {
        UserPrincipal user = new UserPrincipal();
        user.setId(CURRENT_USER_ID);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");

    }

    @Test
    public void shouldDeleteProposalAndRedirectToDashboardWhenIdGiven() throws ServiceException, EntityNotFoundException {
        Proposal proposal = new Proposal();
        proposal.setParticipantId(CURRENT_USER_ID);
        when(proposalService.getProposal(1L)).thenReturn(proposal);

        String view = command.execute(request, response);

        verify(proposalService).deleteProposal(1L);
        assertThat(view, is("redirect:/user-dashboard"));
    }

    @Test
    public void shouldBeBadRequestWhenInvalidIdGiven() {
        when(request.getParameter("id")).thenReturn("invalid id");

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBeForbiddenRequestWhenUserIsNotProposalOwner() throws ServiceException, EntityNotFoundException {
        Proposal proposal = new Proposal();
        Long notCurrentUserId = CURRENT_USER_ID + 1;
        proposal.setParticipantId(notCurrentUserId);
        when(proposalService.getProposal(any())).thenReturn(proposal);

        String view = command.execute(request, response);

        CommandTestHelper.assertThatRequestIsForbidden(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenErrorOccurs() throws ServiceException, EntityNotFoundException {
        when(proposalService.getProposal(any())).thenThrow(new ServiceException());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.helper.Builder;
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
public class ProcessProposalCommandTest {

    private static final long CURRENT_USER_ID = 1L;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Builder<Proposal> builder;

    @Mock
    private ProposalService proposalService;

    @InjectMocks
    private ProcessProposalCommand command;

    @Before
    public void setUp() throws Exception {
        UserPrincipal user = new UserPrincipal(CURRENT_USER_ID, "participant", false);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);
    }

    @Test
    public void shouldCreateProposalAndRedirectToDashboardWhenNoIdGiven() throws ServiceException, InvalidEntityException {
        when(builder.build(request)).thenReturn(new Proposal());

        String view = command.execute(request, response);

        verify(proposalService).createProposal(any());
        assertThat(view, is("redirect:/user-dashboard"));
    }

    @Test
    public void shouldUpdateProposalWhenIdGivenAndUserIsOwner() throws ServiceException, EntityNotFoundException, InvalidEntityException {
        Proposal proposal = new Proposal();
        proposal.setId(1L);
        proposal.setParticipantId(CURRENT_USER_ID);
        when(builder.build(request)).thenReturn(proposal);
        when(proposalService.getProposal(1L)).thenReturn(proposal);

        command.execute(request, response);

        verify(proposalService).updateProposal(proposal);
    }

    @Test
    public void shouldRedirectToDashboardAfterUpdate() throws ServiceException, InvalidEntityException, EntityNotFoundException {
        Proposal proposal = new Proposal();
        proposal.setId(1L);
        proposal.setParticipantId(CURRENT_USER_ID);
        when(builder.build(request)).thenReturn(proposal);
        when(proposalService.getProposal(1L)).thenReturn(proposal);

        String view = command.execute(request, response);

        verify(proposalService).updateProposal(any());
        assertThat(view, is("redirect:/user-dashboard"));
    }

    @Test
    public void shouldBeForbiddenRequestWhenUserIsNotTheOwner() throws ServiceException, EntityNotFoundException {
        Proposal proposal = new Proposal();
        proposal.setId(1L);
        Long notCurrentUserId = CURRENT_USER_ID + 1;
        proposal.setParticipantId(notCurrentUserId);
        when(builder.build(request)).thenReturn(proposal);
        when(proposalService.getProposal(1L)).thenReturn(proposal);

        String view = command.execute(request, response);

        CommandTestHelper.assertThatRequestIsForbidden(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenErrorOccurs() throws ServiceException, InvalidEntityException {
        when(builder.build(request)).thenReturn(new Proposal());
        doThrow(new ServiceException()).when(proposalService).createProposal(any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
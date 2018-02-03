package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
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

    @Test
    public void shouldBeEmptyViewWhenDeleted() throws ServiceException, CommandException {
        UserPrincipal user = new UserPrincipal();
        user.setId(CURRENT_USER_ID);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");

        String view = command.execute(request, response);

        verify(proposalService).deleteProposal(1L, CURRENT_USER_ID);
        assertThat(view, is(emptyString()));
    }
}
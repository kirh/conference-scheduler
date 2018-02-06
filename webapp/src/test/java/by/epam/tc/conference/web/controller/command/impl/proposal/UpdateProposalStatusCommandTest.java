package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
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
public class UpdateProposalStatusCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
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
        UserPrincipal user = new UserPrincipal(1L, "admin", true);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(user);
    }

    @Test
    public void shouldUpdateStatusWhenValidDataGiven() throws ServiceException, CommandException {
        command.execute(request, response);

        verify(proposalService).updateStatus(1L, ProposalStatus.PENDING, 1L);
    }

    @Test
    public void shouldRedirectToProposalAfterUpdate() throws CommandException {
        String view = command.execute(request, response);

        assertThat(view, is("redirect:/proposal?action=show&id=1"));
    }


}
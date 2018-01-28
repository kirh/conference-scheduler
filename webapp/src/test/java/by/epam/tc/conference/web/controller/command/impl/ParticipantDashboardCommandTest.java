package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalDetailsService;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.SessionAttribute;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantDashboardCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProposalDetailsService proposalDetailsService;

    @InjectMocks
    private ParticipantDashboardCommand command;

    @Before
    public void setUp() throws Exception {
        UserPrincipal participant = new UserPrincipal(1L, "participant", false);
        when(request.getSession().getAttribute(SessionAttribute.USER_PRINCIPAL)).thenReturn(participant);
    }

    @Test
    public void shouldSpecifyProposalsAttributeWhenExecute() throws ServiceException, CommandException {
        List<ProposalDetails> proposals = new ArrayList<>();
        when(proposalDetailsService.findProposalsDetailsByParticipantId(1L)).thenReturn(proposals);

        command.execute(request, response);

        verify(request).setAttribute(eq("proposals"), same(proposals));
    }

    @Test
    public void shouldReturnParticipantDashboardViewWhenExecute() throws CommandException {
        String view = command.execute(request, response);

        assertThat(view, is("user-dashboard"));
    }
}
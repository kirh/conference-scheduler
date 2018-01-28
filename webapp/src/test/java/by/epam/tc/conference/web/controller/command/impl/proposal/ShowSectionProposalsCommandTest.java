package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.ProposalDetailsService;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
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
    private ProposalDetailsService proposalDetailsService;

    @InjectMocks
    private ShowSectionProposalsCommand command;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("sectionId")).thenReturn("1");
    }


    @Test
    public void shouldSpecifyProposalsAndReturnSectionViewWhenValidId() throws CommandException {
        String view = command.execute(request, response);

        verify(request).setAttribute(eq("proposals"), anyList());
        assertThat(view, is("section"));
    }


}
package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.controller.SessionAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProposalBuilderTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    private ProposalBuilder builder = new ProposalBuilder();

    @Test
    public void shouldBuildProposalFromGivenRequestWithParams() {
        long id = 1;
        String title = "some title";
        String description = "some description";
        long sectionId = 2;
        long participantId = 3;

        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(request.getParameter("title")).thenReturn(String.valueOf(title));
        when(request.getParameter("description")).thenReturn(String.valueOf(description));
        when(request.getParameter("sectionId")).thenReturn(String.valueOf(sectionId));
        UserPrincipal user = new UserPrincipal();
        user.setId(participantId);
        when(getUser()).thenReturn(user);

        Proposal expected = new Proposal(id, title, description, sectionId, participantId, ProposalStatus.PENDING);
        Proposal actual = builder.build(request);

        assertThat(actual, is(expected));
    }

    private Object getUser() {
        return request.getSession()
                .getAttribute(SessionAttribute.USER_PRINCIPAL);
    }
}
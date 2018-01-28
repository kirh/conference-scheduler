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

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("some title");
        when(request.getParameter("description")).thenReturn("some description");
        when(request.getParameter("sectionId")).thenReturn("2");
        UserPrincipal user = new UserPrincipal();
        user.setId(3L);
        when(getUser()).thenReturn(user);

        Proposal expected = new Proposal(1L, "some title", "some description", 2L, 3L,
                ProposalStatus.PENDING);
        Proposal actual = builder.build(request);

        assertThat(actual, is(expected));
    }

    private Object getUser() {
        return request.getSession()
                .getAttribute(SessionAttribute.USER_PRINCIPAL);
    }
}
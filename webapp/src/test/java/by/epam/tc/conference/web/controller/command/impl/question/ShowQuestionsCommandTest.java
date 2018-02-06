package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowQuestionsCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ShowQuestionsCommand command;


    @Test
    public void shouldSpecifyQuestionsForAdminWhenUserIsAdmin() throws ServiceException, CommandException {
        UserPrincipal admin = new UserPrincipal(1L, "admin", true);
        when(getUser()).thenReturn(admin);

        command.execute(request, response);

        verify(questionService).findQuestionsByAdministratorId(1L);
        verify(request).setAttribute(eq("questions"), anyList());
    }

    @Test
    public void shouldSpecifyQuestionsForParticipantWhenUserIsParticipant() throws ServiceException, CommandException {
        UserPrincipal participant = new UserPrincipal(1L, "participant", false);
        when(getUser()).thenReturn(participant);

        command.execute(request, response);

        verify(questionService).findQuestionsByParticipantId(1L);
        verify(request).setAttribute(eq("questions"), anyList());
    }

    @Test
    public void shouldReturnQuestionsViewWhenSuccessful() throws CommandException {
        UserPrincipal participant = new UserPrincipal(1L, "participant", false);
        when(getUser()).thenReturn(participant);

        String view = command.execute(request, response);

        assertThat(view, is("questions"));
    }

    private Object getUser() {
        return request.getSession()
                .getAttribute(SessionAttribute.USER_PRINCIPAL);
    }
}
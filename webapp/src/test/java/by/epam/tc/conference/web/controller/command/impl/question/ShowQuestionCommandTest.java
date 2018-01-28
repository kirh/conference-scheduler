package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
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
public class ShowQuestionCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private QuestionService questionService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ShowQuestionCommand command;

    @Test
    public void shouldSpecifyQuestionWhenValidQuestionIdGiven() throws CommandException {
        when(request.getParameter("id")).thenReturn("1");

        command.execute(request, response);

        verify(request).setAttribute(eq("question"), any(Question.class));
    }

    @Test
    public void shouldSpecifydMessagesWhenValidQuestionIdGiven() throws CommandException {
        when(request.getParameter("id")).thenReturn("1");

        command.execute(request, response);

        verify(request).setAttribute(eq("messages"), anyList());
    }

    @Test
    public void shouldReturnQuestionViewWhenSuccessful() throws CommandException {
        when(request.getParameter("id")).thenReturn("1");

        String view = command.execute(request, response);

        assertThat(view, is("question"));
    }
}
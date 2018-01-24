package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcessQuestionCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private QuestionService questionService;

    @Mock
    private Builder<Question> questionBuilder;

    @Mock
    private Builder<Message> messageBuilder;


    private ProcessQuestionCommand command;

    @Before
    public void setUp() throws Exception {
        command = new ProcessQuestionCommand(questionService, questionBuilder, messageBuilder);
    }

    @Test
    public void shouldCreateQuestionWhenExecute() throws ServiceException {
        Question question = new Question();
        when(questionBuilder.build(request)).thenReturn(question);
        Message message = new Message();
        when(messageBuilder.build(request)).thenReturn(message);

        command.execute(request, response);

        verify(questionService).createQuestion(question, message);
    }

    @Test
    public void shouldBeBadRequestWhenInvalidDataGiven() throws ServiceException {
        doThrow(new InvalidEntityException()).when(questionService).createQuestion(any(), any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenErrorOccurs() throws ServiceException {
        doThrow(new ServiceException()).when(questionService).createQuestion(any(), any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
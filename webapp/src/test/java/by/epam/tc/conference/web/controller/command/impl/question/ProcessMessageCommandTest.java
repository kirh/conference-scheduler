package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.CommandTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ProcessMessageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Builder<Message> builder;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ProcessMessageCommand command;

    @Test
    public void shouldCreateMessageAndRedirectToQuestionWhenValidDataGiven() throws ServiceException {
        Message message = new Message();
        message.setQuestionId(1L);
        when(builder.build(request)).thenReturn(message);

        String view = command.execute(request, response);

        verify(messageService).createMessage(message);
        assertThat(view, is("redirect:/question?action=show&id=1"));
    }

    @Test
    public void shouldBeBadRequestWhenInvalidMessageDataGiven() throws ServiceException {
        doThrow(new InvalidEntityException()).when(messageService).createMessage(any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatBadRequest(request, response, view);
    }

    @Test
    public void shouldBeInternalErrorWhenErrorOccurs() throws ServiceException {
        doThrow(new ServiceException()).when(messageService).createMessage(any());

        String view = command.execute(request, response);

        CommandTestHelper.assertThatInternalError(request, response, view);
    }
}
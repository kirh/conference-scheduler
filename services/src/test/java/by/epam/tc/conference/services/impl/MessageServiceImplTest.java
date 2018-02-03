package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.MessageDao;
import by.epam.tc.conference.dao.MessageDetailsDao;
import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    @Mock
    private MessageDao messageDao;

    @Mock
    private MessageDetailsDao messageDetailsDao;

    @Mock
    private Validator<Message> validator;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    public void shouldSaveMessageOnCreateWhenValidMessageGiven() throws ServiceException, DaoException {
        Message message = new Message();
        when(validator.validate(message)).thenReturn(true);

        messageService.createMessage(message);

        verify(messageDao).save(message);
    }

    @Test(expected = InvalidDataException.class)
    public void shouldBeExceptionOnCreateWhenInvalidMessageGiven() throws ServiceException {
        Message message = new Message();

        when(validator.validate(message)).thenReturn(false);

        messageService.createMessage(message);
    }

    @Test
    public void shouldBeMessagesWhenFindByQuestionId() throws ServiceException, DaoException {
        List<MessageDetails> messages = Collections.emptyList();

        when(messageDetailsDao.findMessagesByQuestionId(1L)).thenReturn(messages);

        List<MessageDetails> actual = messageService.findMessagesByQuestionId(1L);

        assertThat(actual, is(sameInstance(messages)));
    }
}
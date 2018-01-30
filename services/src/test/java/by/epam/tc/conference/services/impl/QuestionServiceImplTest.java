package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.MessageDao;
import by.epam.tc.conference.dao.QuestionDao;
import by.epam.tc.conference.dao.QuestionDetailsDao;
import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionDetailsDao questionDetailsDao;

    @Mock
    private MessageService messageService;

    @Mock
    private Validator<Question> validator;

    @InjectMocks
    private QuestionServiceImpl questionService;
    private Message message;
    private Question question;

    @Before
    public void setUp() throws Exception {
        message = new Message();
        question = new Question();
    }

    @Test(expected = InvalidDataException.class)
    public void shouldNotCreateQuestionOnCreateWhenInvalidQuestion() throws ServiceException, DaoException {
        when(validator.validate(question)).thenReturn(false);

        questionService.createQuestion(question, message);
        verify(questionDao, never()).save(question);
    }

    @Test
    public void shouldSaveQuestionOnCreate() throws ServiceException, DaoException {
        when(validator.validate(question)).thenReturn(true);
        questionService.createQuestion(question, message);

        verify(questionDao).save(question);
        verify(messageService).createMessage(message);
    }

    @Test
    public void shouldBeQuestionsWhenAdministratorIdGiven() throws ServiceException, DaoException {
        List<QuestionDetails> questions = Collections.emptyList();
        when(questionDetailsDao.findQuestionsByAdminId(1L)).thenReturn(questions);

        List<QuestionDetails> actual = questionService.findQuestionsByAdministratorId(1L);

        assertThat(actual, is(sameInstance(questions)));
    }

    @Test
    public void shouldBeQuestionWhenQuestionIdGiven() throws DaoException, ServiceException {
        when(questionDao.findById(1L)).thenReturn(Optional.of(question));

        Question actual = questionService.getQuestion(1L);
        assertThat(actual, is(sameInstance(question)));
    }

    @Test(expected = NotFoundException.class)
    public void shouldBeExceptionWhenQuestionWithSpecifiedIdNotFound() throws ServiceException, DaoException {
        when(questionDao.findById(1L)).thenReturn(Optional.empty());

        questionService.getQuestion(1L);
    }

    @Test
    public void shouldBeQuestionsWhenParticipantIdGiven() throws DaoException, ServiceException {
        List<QuestionDetails> questions = Collections.emptyList();
        when(questionDetailsDao.findQuestionsByParticipantId(1L)).thenReturn(questions);

        List<QuestionDetails> actual = questionService.findQuestionsByParticipantId(1L);

        assertThat(actual, is(sameInstance(questions)));
    }
}
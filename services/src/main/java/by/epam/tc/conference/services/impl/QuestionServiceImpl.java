package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.MessageDao;
import by.epam.tc.conference.dao.QuestionDao;
import by.epam.tc.conference.dao.QuestionDetailsDao;
import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LogManager.getLogger(QuestionServiceImpl.class);
    private final QuestionDao questionDao;
    private final QuestionDetailsDao questionDetailsDao;
    private final MessageDao messageDao;

    public QuestionServiceImpl(QuestionDao questionDao, QuestionDetailsDao questionDetailsDao, MessageDao messageDao) {
        this.questionDao = questionDao;
        this.questionDetailsDao = questionDetailsDao;
        this.messageDao = messageDao;
    }


    @Override
    public void createQuestion(Question question, Message message) throws ServiceException {
        try {
            questionDao.save(question);
            Long questionId = question.getId();
            message.setQuestionId(questionId);
            messageDao.save(message);
            logger.info("Created question id={}", question.getId());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<QuestionDetails> findQuestionsByAdministratorId(Long id) throws ServiceException {
        try {
            List<QuestionDetails> questions = questionDetailsDao.findQuestionsByAdminId(id);
            logger.info("Found {} questionDetails for administratod id={}", questions.size(), id);
            return questions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Question getQuestion(Long id) throws ServiceException, EntityNotFoundException {
        try {
            Optional<Question> optionalQuestion = questionDao.findById(id);
            Question question = optionalQuestion.orElseThrow(() ->
                    new EntityNotFoundException("Not Found question id=" + id));
            logger.info("conference id={} returned", id);
            return question;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<QuestionDetails> findQuestionsByParticipantId(Long id) throws ServiceException {
        try {
            List<QuestionDetails> questions = questionDetailsDao.findQuestionsByParticipantId(id);
            logger.info("Found {} questionDetails for participant id={}", questions.size(), id);
            return questions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

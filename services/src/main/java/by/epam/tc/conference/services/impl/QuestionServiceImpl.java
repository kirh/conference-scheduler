package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.MessageDao;
import by.epam.tc.conference.dao.QuestionDao;
import by.epam.tc.conference.dao.QuestionDetailsDao;
import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.NotFoundException;
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
    public List<QuestionDetails> findQuestionsByAdministratorId(long administratorId) throws ServiceException {
        try {
            List<QuestionDetails> questions = questionDetailsDao.findQuestionsByAdminId(administratorId);
            logger.info("Found {} questionDetails for administratod id={}", questions.size(), administratorId);
            return questions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Question getQuestion(long questionId) throws ServiceException {
        try {
            Optional<Question> optionalQuestion = questionDao.findById(questionId);
            Question question = optionalQuestion.orElseThrow(() ->
                    new NotFoundException("Not Found question id=" + questionId));
            logger.info("conference id={} returned", questionId);
            return question;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<QuestionDetails> findQuestionsByParticipantId(long participantId) throws ServiceException {
        try {
            List<QuestionDetails> questions = questionDetailsDao.findQuestionsByParticipantId(participantId);
            logger.info("Found {} questionDetails for participant id={}", questions.size(), participantId);
            return questions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

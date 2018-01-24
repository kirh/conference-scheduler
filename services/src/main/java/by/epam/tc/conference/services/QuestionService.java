package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

public interface QuestionService {

    void createQuestion(Question question, Message message) throws InvalidEntityException, ServiceException;

    List<QuestionDetails> findQuestionsByAdministratorId(Long id) throws ServiceException;

    Question getQuestion(Long id) throws EntityNotFoundException, ServiceException;

    List<QuestionDetails> findQuestionsByParticipantId(Long id) throws ServiceException;

}

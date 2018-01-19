package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;

import java.util.List;

public interface QuestionService {

    void createQuestion(Question question, Message message) throws ServiceException;

    List<QuestionDetails> findQuestionsByAdministratorId(Long id) throws ServiceException;

    Question getQuestion(Long id) throws ServiceException;

    List<QuestionDetails> findQuestionsByParticipantId(Long id) throws ServiceException;

}

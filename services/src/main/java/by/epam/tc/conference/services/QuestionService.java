package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains Base operations with questions
 */
public interface QuestionService {

    /**
     * Saves question to underlying persistence data storage and supplies with id
     * @param question to create
     * @param message message related to question
     * @throws InvalidDataException when question or message invalid
     * @throws ServiceException when error during data access occurs
     */
    void createQuestion(Question question, Message message) throws ServiceException;

    /**
     * Returns list of question details for questions related to specified administrator
     * @param administratorId administator identifier
     * @return list of question details for questions related to specified administrator
     * @throws ServiceException when error during data access occurs
     */
    List<QuestionDetails> findQuestionsByAdministratorId(long administratorId) throws ServiceException;

    /**
     * Returns conference with specified id
     * @param questionId question identifier
     * @return conference
     * @throws NotFoundException when conference not found
     * @throws ServiceException when error during data access occurs
     */
    Question getQuestion(long questionId) throws ServiceException;

    /**
     * Returns list of question details for questions asked by specified participant
     * @param participantId participant identifier
     * @return list of question details
     * @throws ServiceException when error during data access occurs
     */
    List<QuestionDetails> findQuestionsByParticipantId(long participantId) throws ServiceException;
}

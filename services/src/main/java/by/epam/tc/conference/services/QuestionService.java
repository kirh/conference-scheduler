package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains Base operations with questions
 */
public interface QuestionService {

    /**
     * Saves question to underlying persistence data storage and supplies with id
     * @param question
     * @param message
     * @throws InvalidEntityException when question or message invalid
     * @throws ServiceException when error during data access occurs
     */
    void createQuestion(Question question, Message message) throws InvalidEntityException, ServiceException;

    /**
     * Returns list of question details for questions related to specified administrator
     * @param id administator identifier
     * @return list of question details for questions related to specified administrator
     * @throws ServiceException when error during data access occurs
     */
    List<QuestionDetails> findQuestionsByAdministratorId(Long id) throws ServiceException;

    /**
     * Returns conference with specified id
     * @param id conference identifier
     * @return conference
     * @throws EntityNotFoundException when conference not found
     * @throws ServiceException when error during data access occurs
     */
    Question getQuestion(Long id) throws EntityNotFoundException, ServiceException;

    /**
     * Returns list of question details for questions asked by specified participant
     * @param id participant identifier
     * @return list of question details
     * @throws ServiceException when error during data access occurs
     */
    List<QuestionDetails> findQuestionsByParticipantId(Long id) throws ServiceException;
}

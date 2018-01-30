package by.epam.tc.conference.dao;

import by.epam.tc.conference.dto.QuestionDetails;

import java.util.List;

/**
 * Represents object to access persisted question details data
 */
public interface QuestionDetailsDao {

    /**
     * Finds details for questions related to administrator's conferences
     *
     * @param id administrator identifier
     * @return list of question details
     * @throws DaoException error during data access occurs
     */
    List<QuestionDetails> findQuestionsByAdminId(long id) throws DaoException;

    /**
     * Finds details for questions asked by specified participant
     *
     * @param id participant identifier
     * @return list of question details
     * @throws DaoException error during data access occurs
     */
    List<QuestionDetails> findQuestionsByParticipantId(long id) throws DaoException;

}

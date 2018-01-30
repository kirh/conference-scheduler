package by.epam.tc.conference.dao;

import by.epam.tc.conference.dto.MessageDetails;

import java.util.List;

/**
 * Represents object to access persisted message details
 */

public interface MessageDetailsDao {

    /**
     * Returns list of message details for messages related to specified question
     *
     * @param id question
     * @return List of message details for messages related to specified question
     * @throws DaoException error during data access occurs
     */
    List<MessageDetails> findMessagesByQuestionId(long id) throws DaoException;

}

package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

/**
 * Contains Base operations with messages
 */
public interface MessageService {

    /**
     * Saves message to underlying persistence data storage and supplies with id
     * @param message to save
     * @throws InvalidEntityException when invalid message given
     * @throws ServiceException when error during data access occurs
     */
    void createMessage(Message message) throws InvalidEntityException, ServiceException;

    /**
     *
     * @param id question identifier
     * @return list of details for messages related to question with specified id
     * @throws ServiceException when error during data access occurs
     */
    List<MessageDetails> findMessagesByQuestionId(Long id) throws ServiceException;

}

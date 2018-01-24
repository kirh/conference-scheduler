package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;

import java.util.List;

public interface MessageService {

    void createMessage(Message message) throws InvalidEntityException, ServiceException;

    List<MessageDetails> findMessagesByQuestionId(Long id) throws ServiceException;

}

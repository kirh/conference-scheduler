package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Message;

import java.util.List;

public interface MessageService {

    void createMessage(Message message) throws ServiceException;

    List<MessageDetails> findMessagesByQuestionId(Long id) throws ServiceException;

}

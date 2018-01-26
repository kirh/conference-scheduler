package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.MessageDao;
import by.epam.tc.conference.dao.MessageDetailsDao;
import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MessageServiceImpl implements MessageService{

    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageDao messageDao;
    private final MessageDetailsDao messageDetailsDao;
    private final Validator<Message> validator;

    public MessageServiceImpl(MessageDao messageDao, MessageDetailsDao messageDetailsDao, Validator<Message> validator) {
        this.messageDao = messageDao;
        this.messageDetailsDao = messageDetailsDao;
        this.validator = validator;
    }

    @Override
    public void createMessage(Message message) throws ServiceException, InvalidEntityException {
        try {
            boolean isValid = validator.validate(message);
            if (!isValid) {
                throw new InvalidEntityException("invalid message: " + message);
            }
            messageDao.save(message);
            logger.info("Created message id={}", message.getId());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<MessageDetails> findMessagesByQuestionId(Long id) throws ServiceException {
        try {
            List<MessageDetails> messages = messageDetailsDao.findMessagesByQuestionId(id);
            logger.info("Found {} messageDetails for question with id={}", messages.size(), id);
            return messages;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

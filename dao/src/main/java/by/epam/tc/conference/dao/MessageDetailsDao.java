package by.epam.tc.conference.dao;

import by.epam.tc.conference.dto.MessageDetails;

import java.util.List;

public interface MessageDetailsDao {
    List<MessageDetails> findMessagesByQuestionId(Long id) throws DaoException;

}

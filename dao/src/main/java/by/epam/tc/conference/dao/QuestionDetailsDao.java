package by.epam.tc.conference.dao;

import by.epam.tc.conference.dto.QuestionDetails;

import java.util.List;

public interface QuestionDetailsDao {

    List<QuestionDetails> findQuestionsByAdminId(Long id) throws DaoException;

    List<QuestionDetails> findQuestionsByParticipantId(Long id) throws DaoException;

}

package by.epam.tc.conference.dao;

import by.epam.tc.conference.dao.connectionpool.ConnectionPool;
import by.epam.tc.conference.dao.connectionpool.ConnectionPoolException;
import by.epam.tc.conference.dao.sql.*;
import by.epam.tc.conference.dao.sql.UserDao;
import by.epam.tc.conference.dao.sql.object.*;
import by.epam.tc.conference.dao.sql.rowmapper.*;
import by.epam.tc.conference.entity.*;

import java.sql.Connection;

public class DaoFactory {

    private static final DaoFactory INSTANSE = new DaoFactory();
    private ConnectionPool connectionPool = new ConnectionPool();

    private DaoFactory() {

    }

    public static DaoFactory getInstanse() {
        return INSTANSE;
    }

    public GenericDao<Conference> getConferenceDao() throws DaoException {
        Executor<Conference> conferenceExecutor = new Executor<>(getConnection());
        ResultHandler<Conference> conferenceResultHandler = new ResultHandler<>(new ConferenceRowMapper());
        return new ConferenceDao(conferenceResultHandler, conferenceExecutor);
    }

    public UserDao getUserDao() throws DaoException {
        Executor<User> userExecutor = null;
        try {
            userExecutor = new Executor<>(getConnection());
        } catch (DaoException e) {
            throw new DaoException(e);
        }
        ResultHandler<User> userResultHandler = new ResultHandler<>(new UserRowMapper());
        return new UserDaoImpl(userResultHandler, userExecutor);
    }

    public GenericDao<Section> getSectionDao() throws DaoException {
        Executor<Section> sectionExecutor = new Executor<>(getConnection());
        ResultHandler<Section> sectionResultHandler = new ResultHandler<>(new SectionRowMapper());
        return new SectionDao(sectionResultHandler, sectionExecutor);
    }

    public GenericDao<Proposal> getProposalDao() throws DaoException {
        Executor<Proposal> proposalExecutor = new Executor<>(getConnection());
        ResultHandler<Proposal> proposalResultHandler = new ResultHandler<>(new ProposalRowMapper());
        return new ProposalDao(proposalResultHandler, proposalExecutor);
    }

    public GenericDao<Question> getQuestionDao() throws DaoException {
        Executor<Question> questionExecutor = new Executor<>(getConnection());
        ResultHandler<Question> questionResultHandler = new ResultHandler<>(new QuestionRowMapper());
        return new QuestionDao(questionResultHandler, questionExecutor);
    }

    public GenericDao<Message> getMessageDao() throws DaoException {
        Executor<Message> messageExecutor = new Executor<>(getConnection());
        ResultHandler<Message> messageResultHandler = new ResultHandler<>(new MessageRowMapper());
        return new MessageDao(messageResultHandler, messageExecutor);
    }

    private Connection getConnection() throws DaoException {
        try {
            return connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }
}

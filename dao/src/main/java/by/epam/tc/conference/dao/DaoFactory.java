package by.epam.tc.conference.dao;

import by.epam.tc.conference.dao.sql.*;
import by.epam.tc.conference.dao.sql.object.*;
import by.epam.tc.conference.dao.sql.rowmapper.*;
import by.epam.tc.conference.entity.*;

import java.sql.Connection;

public class DaoFactory {

    private static final DaoFactory INSTANSE = new DaoFactory();

    private DaoFactory() {

    }

    public static DaoFactory getInstanse() {
        return INSTANSE;
    }

    public GenericDao<Long, Conference> getConferenceDao() {
        Executor<Long, Conference> conferenceExecutor = new Executor<>(getConnection());
        ResultHandler<Conference> conferenceResultHandler = new ResultHandler<>(new ConferenceRowMapper());
        return new ConferenceDao(conferenceResultHandler, conferenceExecutor);
    }

    public GenericDao<Long, User> getUserDao() {
        Executor<Long, User> userExecutor = new Executor<>(getConnection());
        ResultHandler<User> userResultHandler = new ResultHandler<>(new UserRowMapper());
        return new UserDao(userResultHandler, userExecutor);
    }

    public GenericDao<Long, Section> getSectionDao() {
        Executor<Long, Section> sectionExecutor = new Executor<>(getConnection());
        ResultHandler<Section> sectionResultHandler = new ResultHandler<>(new SectionRowMapper());
        return new SectionDao(sectionResultHandler, sectionExecutor);
    }

    public GenericDao<Long, Proposal> getProposalDao() {
        Executor<Long, Proposal> proposalExecutor = new Executor<>(getConnection());
        ResultHandler<Proposal> proposalResultHandler = new ResultHandler<>(new ProposalRowMapper());
        return new ProposalDao(proposalResultHandler, proposalExecutor);
    }

    public GenericDao<Long, Question> getQuestionDao() {
        Executor<Long, Question> questionExecutor = new Executor<>(getConnection());
        ResultHandler<Question> questionResultHandler = new ResultHandler<>(new QuestionRowMapper());
        return new QuestionDao(questionResultHandler, questionExecutor);
    }

    public GenericDao<Long, Message> getMessageDao() {
        Executor<Long, Message> messageExecutor = new Executor<>(getConnection());
        ResultHandler<Message> messageResultHandler = new ResultHandler<>(new MessageRowMapper());
        return new MessageDao(messageResultHandler, messageExecutor);
    }

    private Connection getConnection() {
        return null;
    }

}

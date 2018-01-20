package by.epam.tc.conference.dao.mysql;

import by.epam.tc.conference.dao.*;
import by.epam.tc.conference.dao.mysql.pool.ConnectionPool;
import by.epam.tc.conference.dao.mysql.pool.Connector;
import by.epam.tc.conference.dao.mysql.pool.ConnectorImpl;
import by.epam.tc.conference.dao.mysql.impl.*;
import by.epam.tc.conference.dao.mysql.rowmapper.*;
import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.*;

public class MysqlDaoFactory extends DaoFactory {

    private final ConferenceDao conferenceDao;
    private final UserDao userDao;
    private final SectionDao sectionDao;
    private final ProposalDao proposalDao;
    private final ProposalDetailsDao proposalDetailsDao;
    private final QuestionDao questionDao;
    private final QuestionDetailsDao questionDetailsDao;
    private final MessageDao messageDao;
    private final MessageDetailsDao messageDetailsDao;
    private final TransactionManager transactionManager;

    public MysqlDaoFactory() {

        final ConnectionPool connectionPool = ConnectionPool.getInstance();
        final Connector connector = new ConnectorImpl(connectionPool);

        Executor<Conference> conferenceExecutor = new Executor<>(connector, new ConferenceRowMapper());
        conferenceDao = new ConferenceDaoImpl(conferenceExecutor);

        Executor<User> userExecutor = new Executor<>(connector, new UserRowMapper());
        userDao = new UserDaoImpl(userExecutor);

        Executor<Section> sectionExecutor = new Executor<>(connector, new SectionRowMapper());
        sectionDao = new SectionDaoImpl(sectionExecutor);

        Executor<Proposal> proposalExecutor = new Executor<>(connector, new ProposalRowMapper());
        proposalDao = new ProposalDaoImpl(proposalExecutor);

        Executor<Question> questionExecutor = new Executor<>(connector, new QuestionRowMapper());
        questionDao = new QuestionDaoImpl(questionExecutor);

        Executor<Message> messageExecutor = new Executor<>(connector, new MessageRowMapper());
        messageDao = new MessageDaoImpl(messageExecutor);

        Executor<MessageDetails> messageDetailsExecutor = new Executor<>(connector, new MessageDetailRowMapper());
        messageDetailsDao = new MessageDetailsDaoImpl(messageDetailsExecutor);

        Executor<QuestionDetails> questionDetailsExecutor = new Executor<>(connector, new QuestionDetailsRowMapper());
        questionDetailsDao = new QuestionDetailsDaoImpl(questionDetailsExecutor);

        Executor<ProposalDetails> proposalDetailsExecutor = new Executor<>(connector, new ProposalDetailsRowMapper());
        proposalDetailsDao = new ProposalDetailsDaoImpl(proposalDetailsExecutor);

        transactionManager = new TransactionManagerImpl(connector);
    }

    public ConferenceDao getConferenceDao() {
        return conferenceDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SectionDao getSectionDao() {
        return sectionDao;
    }

    public ProposalDao getProposalDao() {
        return proposalDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    @Override
    public MessageDetailsDao getMessageDetailsDao() {
        return messageDetailsDao;
    }

    @Override
    public QuestionDetailsDao getQuestionDetailsDao() {
        return questionDetailsDao;
    }

    public ProposalDetailsDao getProposalDetailsDao() {
        return proposalDetailsDao;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

}

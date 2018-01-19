package by.epam.tc.conference.dao.mysql;

import by.epam.tc.conference.dao.*;
import by.epam.tc.conference.dao.mysql.connectionpool.ConnectionPool;
import by.epam.tc.conference.dao.mysql.connectionpool.ConnectionPoolException;
import by.epam.tc.conference.dao.mysql.connectionpool.Connector;
import by.epam.tc.conference.dao.mysql.connectionpool.ConnectorImpl;
import by.epam.tc.conference.dao.mysql.impl.*;
import by.epam.tc.conference.dao.mysql.rowmapper.*;
import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MysqlDaoFactory extends DaoFactory {

    private static final Logger logger = LogManager.getLogger(MysqlDaoFactory.class);
    private static ConnectionPool connectionPool;

    static {
        try {
            connectionPool = new ConnectionPool();
        } catch (ConnectionPoolException e) {
            logger.error("Connection pool construction failed", e);

        }
    }

    private Connector connector = new ConnectorImpl(connectionPool);

    public ConferenceDao getConferenceDao() {
        ResultHandler<Conference> conferenceResultHandler = new ResultHandler<>(new ConferenceRowMapper());
        Executor<Conference> conferenceExecutor = new Executor<>(connector, conferenceResultHandler);
        return new ConferenceDaoImpl(conferenceExecutor);
    }

    public UserDao getUserDao() {
        ResultHandler<User> userResultHandler = new ResultHandler<>(new UserRowMapper());
        Executor<User> userExecutor = new Executor<>(connector, userResultHandler);
        return new UserDaoImpl(userExecutor);
    }

    public SectionDao getSectionDao() {
        ResultHandler<Section> sectionResultHandler = new ResultHandler<>(new SectionRowMapper());
        Executor<Section> sectionExecutor = new Executor<>(connector, sectionResultHandler);
        return new SectionDaoImpl(sectionExecutor);
    }

    public ProposalDao getProposalDao() {
        ResultHandler<Proposal> proposalResultHandler = new ResultHandler<Proposal>(new ProposalRowMapper());
        Executor<Proposal> proposalExecutor = new Executor<>(connector, proposalResultHandler);
        return new ProposalDaoImpl(proposalExecutor);
    }

    public QuestionDao getQuestionDao() {
        ResultHandler<Question> questionResultHandler = new ResultHandler<>(new QuestionRowMapper());
        Executor<Question> questionExecutor = new Executor<>(connector, questionResultHandler);
        return new QuestionDaoImpl(questionExecutor);
    }

    public MessageDao getMessageDao() {
        ResultHandler<Message> messageResultHandler = new ResultHandler<>(new MessageRowMapper());
        Executor<Message> messageExecutor = new Executor<>(connector, messageResultHandler);
        return new MessageDaoImpl(messageExecutor);
    }

    @Override
    public MessageDetailsDao getMessageDetailsDao() {
        ResultHandler<MessageDetails> messageDetailsHander = new ResultHandler<>(new MessageDetailRowMapper());
        Executor<MessageDetails> executor = new Executor<>(connector, messageDetailsHander);
        return new MessageDetailsDaoImpl(executor);
    }

    @Override
    public QuestionDetailsDao getQuestionDetailsDao() {
        ResultHandler<QuestionDetails> questionDetailsHandler = new ResultHandler<>(new QuestionDetailsRowMapper());
        Executor<QuestionDetails> executor = new Executor<>(connector, questionDetailsHandler);
        return new QuestionDetailsDaoImpl(executor);
    }
    
    public ProposalDetailsDao getProposalDetailsDao() {
        ResultHandler<ProposalDetails> proposalDetailsHandler = new ResultHandler<>(new ProposalDetailsRowMapper());
        Executor<ProposalDetails> executor = new Executor<>(connector, proposalDetailsHandler);
        return new ProposalDetailsDaoImpl(executor);
    }



    @Override
    public TransactionManager getTransactionManager() {
        return new TransactionManagerImpl(connector);
    }

}

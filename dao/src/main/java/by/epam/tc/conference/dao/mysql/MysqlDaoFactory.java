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

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final Connector connector = new ConnectorImpl(connectionPool);

    public ConferenceDao getConferenceDao() {
        Executor<Conference> conferenceExecutor = new Executor<>(connector, new ConferenceRowMapper());
        return new ConferenceDaoImpl(conferenceExecutor);
    }

    public UserDao getUserDao() {
        Executor<User> userExecutor = new Executor<>(connector, new UserRowMapper());
        return new UserDaoImpl(userExecutor);
    }

    public SectionDao getSectionDao() {
        Executor<Section> sectionExecutor = new Executor<>(connector, new SectionRowMapper());
        return new SectionDaoImpl(sectionExecutor);
    }

    public ProposalDao getProposalDao() {
        Executor<Proposal> proposalExecutor = new Executor<>(connector, new ProposalRowMapper());
        return new ProposalDaoImpl(proposalExecutor);
    }

    public QuestionDao getQuestionDao() {
        Executor<Question> questionExecutor = new Executor<>(connector, new QuestionRowMapper());
        return new QuestionDaoImpl(questionExecutor);
    }

    public MessageDao getMessageDao() {
        Executor<Message> messageExecutor = new Executor<>(connector, new MessageRowMapper());
        return new MessageDaoImpl(messageExecutor);
    }

    @Override
    public MessageDetailsDao getMessageDetailsDao() {
        Executor<MessageDetails> executor = new Executor<>(connector, new MessageDetailRowMapper());
        return new MessageDetailsDaoImpl(executor);
    }

    @Override
    public QuestionDetailsDao getQuestionDetailsDao() {
        Executor<QuestionDetails> executor = new Executor<>(connector, new QuestionDetailsRowMapper());
        return new QuestionDetailsDaoImpl(executor);
    }
    
    public ProposalDetailsDao getProposalDetailsDao() {
        Executor<ProposalDetails> executor = new Executor<>(connector, new ProposalDetailsRowMapper());
        return new ProposalDetailsDaoImpl(executor);
    }



    @Override
    public TransactionManager getTransactionManager() {
        return new TransactionManagerImpl(connector);
    }

}

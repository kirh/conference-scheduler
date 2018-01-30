package by.epam.tc.conference.dao;

import by.epam.tc.conference.dao.mysql.MysqlDaoFactory;

/**
 * General factory for data access layer
 */
public abstract class DaoFactory {

    private static final DaoFactory INSTANCE = new MysqlDaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public abstract ConferenceDao getConferenceDao();

    public abstract UserDao getUserDao();

    public abstract SectionDao getSectionDao();

    public abstract ProposalDao getProposalDao();

    public abstract QuestionDao getQuestionDao();

    public abstract MessageDao getMessageDao();

    public abstract MessageDetailsDao getMessageDetailsDao();

    public abstract QuestionDetailsDao getQuestionDetailsDao();

    public abstract ProposalDetailsDao getProposalDetailsDao();

    public abstract TransactionManager getTransactionManager();
}

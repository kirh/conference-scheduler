package by.epam.tc.conference.services;

import by.epam.tc.conference.dao.*;
import by.epam.tc.conference.dao.mysql.MessageDetailsDao;
import by.epam.tc.conference.services.impl.*;
import by.epam.tc.conference.services.validator.UserValidator;

public class ServiceFactory {


    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        UserDao userDao = daoFactory.getUserDao();
        UserValidator userValidator = new UserValidator();
        return new UserServiceImpl(userDao, userValidator);
    }

    public ConferenceService getConferenceService() {
        ConferenceDao conferenceDao = daoFactory.getConferenceDao();
        return new ConferenceServiceImpl(conferenceDao);
    }

    public SectionService getSectionService() {
        SectionDao sectionDao = daoFactory.getSectionDao();
        return new SectionServiceImpl(sectionDao);
    }

    public ProposalService getProposalService() {
        ProposalDao proposalDao = daoFactory.getProposalDao();
        ProposalDetailsDao proposalDetailsDao = daoFactory.getProposalDetailsDao();
        return new ProposalServiceImpl(proposalDao, proposalDetailsDao);
    }

    public QuestionService getQuestionService() {
        MessageDao messageDao = daoFactory.getMessageDao();
        QuestionDao questionDao = daoFactory.getQuestionDao();
        QuestionDetailsDao questionDetailsDao = daoFactory.getQuestionDetailsDao();
        return new QuestionServiceImpl(questionDao, questionDetailsDao, messageDao);
    }

    public MessageService getMessageService() {
        MessageDao messageDao = daoFactory.getMessageDao();
        MessageDetailsDao messageDetailsDao = daoFactory.getMessageDetailsDao();
        return new MessageServiceImpl(messageDao, messageDetailsDao);
    }
}

package by.epam.tc.conference.services;

import by.epam.tc.conference.dao.*;
import by.epam.tc.conference.services.impl.*;
import by.epam.tc.conference.services.validator.*;

/**
 * Factory for service layer
 */
public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final ProposalService proposalService;
    private final QuestionService questionService;
    private final MessageService messageService;

    private ServiceFactory() {
        DaoFactory daoFactory = DaoFactory.getInstance();

        UserDao userDao = daoFactory.getUserDao();
        userService = new UserServiceImpl(userDao, new UserValidator());

        ConferenceDao conferenceDao = daoFactory.getConferenceDao();
        conferenceService = new ConferenceServiceImpl(conferenceDao, new ConferenceValidator());

        SectionDao sectionDao = daoFactory.getSectionDao();
        sectionService = new SectionServiceImpl(sectionDao, new SectionValidator());

        ProposalDao proposalDao = daoFactory.getProposalDao();
        ProposalDetailsDao proposalDetailsDao = daoFactory.getProposalDetailsDao();
        proposalService = new ProposalServiceImpl(proposalDao, proposalDetailsDao, new ProposalValidator());

        QuestionDao questionDao = daoFactory.getQuestionDao();
        QuestionDetailsDao questionDetailsDao = daoFactory.getQuestionDetailsDao();
        MessageDao messageDao = daoFactory.getMessageDao();
        questionService = new QuestionServiceImpl(questionDao, questionDetailsDao, messageDao);

        MessageDetailsDao messageDetailsDao = daoFactory.getMessageDetailsDao();
        messageService = new MessageServiceImpl(messageDao, messageDetailsDao, new MessageValidator());
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public ConferenceService getConferenceService() {
        return conferenceService;
    }

    public SectionService getSectionService() {
        return sectionService;
    }

    public ProposalService getProposalService() {
        return proposalService;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public MessageService getMessageService() {
        return messageService;
    }
}

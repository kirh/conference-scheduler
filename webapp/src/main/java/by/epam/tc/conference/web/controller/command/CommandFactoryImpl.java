package by.epam.tc.conference.web.controller.command;

import by.epam.tc.conference.services.*;
import by.epam.tc.conference.web.controller.command.helper.ConferenceBuilder;
import by.epam.tc.conference.web.controller.command.helper.UserBuilder;
import by.epam.tc.conference.web.controller.command.impl.*;

import java.util.EnumMap;

public class CommandFactoryImpl extends CommandFactory {

    private final EnumMap<CommandEnum, Command> commands = new EnumMap<>(CommandEnum.class);

    public CommandFactoryImpl() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        commands.put(CommandEnum.LOGIN, new LoginCommand(userService));
        commands.put(CommandEnum.LOGOUT, new LogoutCommand());
        commands.put(CommandEnum.CHANGE_LOCALE, new ChangeLocaleCommand());
        ConferenceService conferenceService = serviceFactory.getConferenceService();
        commands.put(CommandEnum.ADMIN_DASHBOARD, new AdminDashboardCommand(conferenceService));
        SectionService sectionService = serviceFactory.getSectionService();
        commands.put(CommandEnum.CONFERENCE, new ConferenceCommand(new ConferenceBuilder(), conferenceService,
                sectionService));
        ProposalService proposalService = serviceFactory.getProposalService();
        commands.put(CommandEnum.PARTICIPANT_DASHBOARD, new ParticipantDashboardCommand(proposalService));
        commands.put(CommandEnum.REGISTER, new RegisterCommand(new UserBuilder(), userService));
        commands.put(CommandEnum.PROPOSAL, new ProposalCommand(proposalService));
        commands.put(CommandEnum.SECTION, new SectionCommand(sectionService));
        QuestionService questionService = serviceFactory.getQuestionService();
        MessageService messageService = serviceFactory.getMessageService();
        commands.put(CommandEnum.QUESTION, new QuestionCommand(questionService, messageService));
        commands.put(CommandEnum.UNKNOWN, new UnknownCommand());
    }

    @Override
    public Command getCommand(String path) {
        CommandEnum command = CommandEnum.getCommand(path);
        return commands.get(command);
    }
}

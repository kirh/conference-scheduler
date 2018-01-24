package by.epam.tc.conference.web.controller.command;

import by.epam.tc.conference.services.*;
import by.epam.tc.conference.web.controller.command.helper.*;
import by.epam.tc.conference.web.controller.command.impl.*;
import by.epam.tc.conference.web.controller.command.impl.conference.*;
import by.epam.tc.conference.web.controller.command.impl.proposal.*;
import by.epam.tc.conference.web.controller.command.impl.question.ProcessMessageCommand;
import by.epam.tc.conference.web.controller.command.impl.question.ProcessQuestionCommand;
import by.epam.tc.conference.web.controller.command.impl.question.ShowQuestionCommand;
import by.epam.tc.conference.web.controller.command.impl.question.ShowQuestionsCommand;
import by.epam.tc.conference.web.controller.command.impl.section.DeleteSectionCommand;
import by.epam.tc.conference.web.controller.command.impl.section.ProcessSectionCommand;
import by.epam.tc.conference.web.controller.command.impl.section.UpdateSectionCommand;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandFactoryImpl extends CommandFactory {

    private final Map<String, Command> commands;
    private final Command unknownCommand = new UnknownCommand();

    public CommandFactoryImpl() {
        Map<String, Command> commands = new HashMap<>();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        commands.put("", new RootPageCommand());
        commands.put("conference?add", (request, response) -> "conference-form");
        ConferenceService conferenceService = serviceFactory.getConferenceService();
        commands.put("conference?delete", new DeleteConferenceCommand(conferenceService));
        commands.put("conference?process", new ProcessConferenceCommand(new ConferenceBuilder(), conferenceService));
        SectionService sectionService = serviceFactory.getSectionService();
        commands.put("conference?show", new ShowConferenceCommand(conferenceService, sectionService));
        commands.put("conference?showAll", new ShowAllConferencesCommand(conferenceService));
        commands.put("conference?update", new UpdateConferenceCommand(conferenceService));
        commands.put("proposal?create", (request, response) -> "proposal-form");
        ProposalService proposalService = serviceFactory.getProposalService();
        commands.put("proposal?delete", new DeleteProposalCommand(proposalService));
        commands.put("proposal?process", new ProcessProposalCommand(proposalService, new ProposalBuilder()));
        commands.put("proposal?showAll", new ShowSectionProposalsCommand(proposalService));
        commands.put("proposal?show", new ShowProposalCommand(proposalService));
        commands.put("proposal?update", new UpdateProposalStatusCommand(proposalService));
        QuestionService questionService = serviceFactory.getQuestionService();
        commands.put("question?process", new ProcessQuestionCommand(questionService, new QuestionBuilder(), new MessageBuilder()));
        MessageService messageService = serviceFactory.getMessageService();
        commands.put("question?show", new ShowQuestionCommand(questionService, messageService));
        commands.put("question?showAll", new ShowQuestionsCommand(questionService));
        commands.put("question?create", (request, response) -> "question-form");
        commands.put("question?processMessage", new ProcessMessageCommand(messageService, new MessageBuilder()));
        commands.put("section?delete", new DeleteSectionCommand(sectionService));
        commands.put("section?process", new ProcessSectionCommand(new SectionBuilder(), sectionService));
        commands.put("section?update", new UpdateSectionCommand(sectionService));
        commands.put("section?create", (request, response) -> "section-form");
        commands.put("admin-dashboard", new AdminDashboardCommand(conferenceService));
        commands.put("change-locale", new ChangeLocaleCommand());
        commands.put("login", (request, response) -> "login");
        commands.put("login?process", new ProcessLoginCommand(userService));
        commands.put("logout", new LogoutCommand());
        commands.put("user-dashboard", new ParticipantDashboardCommand(proposalService));
        commands.put("register?process", new ProcessRegisterCommand(userService, new UserBuilder()));
        commands.put("register", (request, response) -> "register");
        this.commands = Collections.unmodifiableMap(commands);
    }

    @Override
    public Command getCommand(String command) {
        return commands.getOrDefault(command, unknownCommand);
    }

}

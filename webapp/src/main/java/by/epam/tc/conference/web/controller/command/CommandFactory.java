package by.epam.tc.conference.web.controller.command;

import by.epam.tc.conference.services.*;
import by.epam.tc.conference.web.ErrorMessage;
import by.epam.tc.conference.web.controller.command.bulder.*;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CommandFactory {

    private static final CommandFactory FACTORY = new CommandFactory();

    private final Map<String, Command> commands;

    private CommandFactory() {
        Map<String, Command> commands = new HashMap<>();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        ConferenceService conferenceService = serviceFactory.getConferenceService();
        SectionService sectionService = serviceFactory.getSectionService();
        ProposalService proposalService = serviceFactory.getProposalService();
        ProposalDetailsService proposalDetailsService = serviceFactory.getProposalDetailsService();
        QuestionService questionService = serviceFactory.getQuestionService();
        MessageService messageService = serviceFactory.getMessageService();
        commands.put("", new RootPageCommand());
        commands.put("conference?action=add", (request, response) -> "conference-form");
        commands.put("conference?action=delete", new DeleteConferenceCommand(conferenceService));
        commands.put("conference?action=process", new ProcessConferenceCommand(new ConferenceBuilder(), conferenceService));
        commands.put("conference?action=show", new ShowConferenceCommand(conferenceService, sectionService));
        commands.put("conference?action=showAll", new ShowAllConferencesCommand(conferenceService));
        commands.put("conference?action=update", new UpdateConferenceCommand(conferenceService));
        commands.put("proposal?action=create", (request, response) -> "proposal-form");
        commands.put("proposal?action=delete", new DeleteProposalCommand(proposalService));
        commands.put("proposal?action=process", new ProcessProposalCommand(proposalService, new ProposalBuilder()));
        commands.put("proposal?action=showAll", new ShowSectionProposalsCommand(proposalDetailsService));
        commands.put("proposal?action=show", new ShowProposalCommand(proposalService));
        commands.put("proposal?action=update", new UpdateProposalStatusCommand(proposalService));
        commands.put("question?action=process", new ProcessQuestionCommand(questionService, new QuestionBuilder(), new MessageBuilder()));
        commands.put("question?action=show", new ShowQuestionCommand(questionService, messageService));
        commands.put("question?action=showAll", new ShowQuestionsCommand(questionService));
        commands.put("question?action=create", (request, response) -> "question-form");
        commands.put("question?action=processMessage", new ProcessMessageCommand(messageService, new MessageBuilder()));
        commands.put("section?action=delete", new DeleteSectionCommand(sectionService));
        commands.put("section?action=process", new ProcessSectionCommand(new SectionBuilder(), sectionService));
        commands.put("section?action=update", new UpdateSectionCommand(sectionService));
        commands.put("section?action=create", (request, response) -> "section-form");
        commands.put("admin-dashboard", new AdminDashboardCommand(conferenceService));
        commands.put("change-locale", new ChangeLocaleCommand());
        commands.put("login", (request, response) -> "login");
        commands.put("login?action=process", new ProcessLoginCommand(userService));
        commands.put("logout", new LogoutCommand());
        commands.put("user-dashboard", new ParticipantDashboardCommand(proposalDetailsService));
        commands.put("register?action=process", new ProcessRegisterCommand(userService, new UserBuilder()));
        commands.put("register", (request, response) -> "register");

        this.commands = Collections.unmodifiableMap(commands);
    }

    public static CommandFactory getInstance() {
        return FACTORY;
    }

    public Command getCommand(String commandString) throws CommandException {
        Command command = commands.get(commandString);
        if (command == null) {
            throw new CommandException("Command not found for '" + commandString + "'", HttpServletResponse
                    .SC_NOT_FOUND, ErrorMessage.NOT_FOUND);
        }
        return command;
    }
}

package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.MessageBuilder;
import by.epam.tc.conference.web.controller.command.helper.QuestionBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class QuestionCommand extends AbstractCommand {

    private static final String QUESTION_ID_PARAM = "id";
    private static final String CONFERENCE_ID_PARAM = "conferenceId";

    private final QuestionService questionService;
    private final MessageService messageService;
    private final Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions;

    public QuestionCommand(QuestionService questionService, MessageService messageService) {
        this.questionService = questionService;
        this.messageService = messageService;

        Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions = new HashMap<>();
        actions.put("show", this::show);
        actions.put("showAll", this::showAll);
        actions.put("process", this::processQuestion);
        actions.put("create", (request, response) -> "question-form");
        actions.put("processMessage", this::createMessage);
        this.actions = Collections.unmodifiableMap(actions);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String actionParameter = request.getParameter("action");
        BiFunction<HttpServletRequest, HttpServletResponse, String> action = actions.get(actionParameter);
        if (action == null) {
            return processPageNotFound(request, response);
        }
        return action.apply(request, response);
    }

    private String show(HttpServletRequest request, HttpServletResponse response) {
        String questionIdString = request.getParameter(QUESTION_ID_PARAM);
        if (questionIdString == null) {
            return processPageNotFound(request, response);
        }
        Long questionId = Long.valueOf(questionIdString);
        String query;
        try {
            Question question = questionService.getQuestion(questionId);
            request.setAttribute("question", question);
            List<MessageDetails> messages = messageService.findMessagesByQuestionId(questionId);
            request.setAttribute("messages", messages);
            return "question";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }

    private String showAll(HttpServletRequest request, HttpServletResponse response) {
        UserPrincipal user = getUser(request);
        Long id = user.getId();
        List<QuestionDetails> questions;
        try {
            if (user.isAdmin()) {
                questions = questionService.findQuestionsByAdministratorId(id);
            } else {
                questions = questionService.findQuestionsByParticipantId(id);
            }
            request.setAttribute("questions", questions);
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
        return "questions";
    }

    private String processQuestion(HttpServletRequest request, HttpServletResponse response) {
        Question question = new QuestionBuilder().build(request);
        Message message = new MessageBuilder().build(request);
        try {
            questionService.createQuestion(question, message);
            return "redirect:/conference?action=show&id=" + question.getConferenceId();
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }

    private String createMessage(HttpServletRequest request, HttpServletResponse response) {
        Message message = new MessageBuilder().build(request);
        try {
            messageService.createMessage(message);
            return "redirect:/question?action=show&id=" + message.getQuestionId();
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

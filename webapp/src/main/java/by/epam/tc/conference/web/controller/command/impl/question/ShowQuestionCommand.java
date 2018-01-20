package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionCommand extends AbstractCommand {
    private static final String QUESTION_ID_PARAM = "id";
    private final QuestionService questionService;
    private final MessageService messageService;

    public ShowQuestionCommand(QuestionService questionService, MessageService messageService) {
        this.questionService = questionService;
        this.messageService = messageService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
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
}

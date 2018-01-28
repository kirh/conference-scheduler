package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowQuestionsCommand.class);
    private static final String QUESTION_ID_PARAM = "id";
    private static final String QUESTION_ATTRIBUTE = "question";
    private static final String MESSAGES_ATTRIBUTE = "messages";
    private static final String QUESTION_VIEW = "question";

    private final QuestionService questionService;
    private final MessageService messageService;

    public ShowQuestionCommand(QuestionService questionService, MessageService messageService) {
        this.questionService = questionService;
        this.messageService = messageService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long questionId = parseIdParameter(request, QUESTION_ID_PARAM);
            logger.debug("Show question id={}", questionId);
            Question question = questionService.getQuestion(questionId);
            request.setAttribute(QUESTION_ATTRIBUTE, question);
            List<MessageDetails> messages = messageService.findMessagesByQuestionId(questionId);
            request.setAttribute(MESSAGES_ATTRIBUTE, messages);
            return QUESTION_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to show question");
        }
    }
}

package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.dto.MessageDetails;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowQuestionsCommand.class);
    private static final String QUESTION_ID_PARAM = "id";
    private final QuestionService questionService;
    private final MessageService messageService;

    public ShowQuestionCommand(QuestionService questionService, MessageService messageService) {
        this.questionService = questionService;
        this.messageService = messageService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long questionId = parseIdParameter(request, QUESTION_ID_PARAM);
        if (questionId == null) {
            return processBadRequest(request, response);
        }
        String query;
        try {
            Question question = questionService.getQuestion(questionId);
            request.setAttribute("question", question);
            List<MessageDetails> messages = messageService.findMessagesByQuestionId(questionId);
            request.setAttribute("messages", messages);
            return "question";
        } catch (ServiceException e) {
            logger.error("Failed to show question id={}", questionId, e);
            query = processInternalError(request, response);
        } catch (EntityNotFoundException e) {
            logger.debug("Failed to show question id={}", questionId, e);
            return processPageNotFound(request, response);
        }
        return query;
    }
}

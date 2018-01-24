package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessQuestionCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessQuestionCommand.class);
    private final QuestionService questionService;
    private final Builder<? extends Question> questionBuilder;
    private final Builder<? extends Message> messageBuilder;

    public ProcessQuestionCommand(QuestionService questionService, Builder<? extends Question> questionBuilder,
                                  Builder<? extends Message> messageBuilder) {
        this.questionService = questionService;
        this.questionBuilder = questionBuilder;
        this.messageBuilder = messageBuilder;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Question question = questionBuilder.build(request);
        Message message = messageBuilder.build(request);
        try {
            questionService.createQuestion(question, message);
            String query = "redirect:/conference?action=show&id=" + question.getConferenceId();
            return logger.traceExit(query);
        } catch (InvalidEntityException e) {
            logger.debug("Failed to process question {} {}", question, message, e);
            return processBadRequest(request, response);
        } catch (ServiceException e) {
            logger.debug("Failed to process question {} {}", question, message, e);
            return processInternalError(request, response);
        }
    }
}

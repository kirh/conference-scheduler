package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessMessageCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessMessageCommand.class);
    private final MessageService messageService;
    private final Builder<? extends Message> builder;

    public ProcessMessageCommand(MessageService messageService, Builder<? extends Message> builder) {
        this.messageService = messageService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Message message = builder.build(request);
        try {
            messageService.createMessage(message);
            return "redirect:/question?action=show&id=" + message.getQuestionId();
        } catch (InvalidEntityException e) {
            logger.debug("Failed to process message {}", message, e);
            return processBadRequest(request, response);
        } catch (ServiceException e) {
            logger.error("Failed to process message {}", message, e);
            return processInternalError(request, response);
        }
    }
}

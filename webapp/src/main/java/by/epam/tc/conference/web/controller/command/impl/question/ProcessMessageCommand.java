package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.services.MessageService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.MessageBuilder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessMessageCommand extends AbstractCommand {
    private MessageService messageService;

    public ProcessMessageCommand(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Message message = new MessageBuilder().build(request);
        try {
            messageService.createMessage(message);
            return "redirect:/question?action=show&id=" + message.getQuestionId();
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

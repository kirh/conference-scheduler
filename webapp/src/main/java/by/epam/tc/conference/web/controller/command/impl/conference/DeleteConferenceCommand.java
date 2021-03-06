package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.Dispatcher;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Removes conference with id specified in request parameter.
 */
public class DeleteConferenceCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(DeleteConferenceCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private final ConferenceService conferenceService;

    public DeleteConferenceCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long conferenceId = parseIdParameter(request, CONFERENCE_ID_PARAM);
            logger.debug("Deleting conference id={}", conferenceId);
            long userId = getUserId(request);
            conferenceService.deleteConferenceById(conferenceId, userId);
            return Dispatcher.SKIP;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to delete conference");
        }
    }
}

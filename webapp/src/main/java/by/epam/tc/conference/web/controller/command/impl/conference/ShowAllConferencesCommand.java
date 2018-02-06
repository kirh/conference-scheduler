package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Adds conferences to request attributes and returns conferences view.
 */
public class ShowAllConferencesCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowAllConferencesCommand.class);
    private static final String CONFERENCES_ATTRIBUTE = "conferences";
    private static final String CONFERENCES_VIEW = "conferences";
    private final ConferenceService conferenceService;

    public ShowAllConferencesCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            logger.debug("Show all conferences");
            List<Conference> conferences = conferenceService.getAllUpComingConferences();
            request.setAttribute(CONFERENCES_ATTRIBUTE, conferences);
            return CONFERENCES_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to show all conferences");
        }
    }
}

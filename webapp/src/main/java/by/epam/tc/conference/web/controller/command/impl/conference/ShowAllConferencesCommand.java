package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllConferencesCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(ShowAllConferencesCommand.class);
    private final ConferenceService conferenceService;

    public ShowAllConferencesCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String query;
        try {
            List<Conference> conferences = conferenceService.getAllConferences();
            request.setAttribute("conferences", conferences);
            return  "conferences";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

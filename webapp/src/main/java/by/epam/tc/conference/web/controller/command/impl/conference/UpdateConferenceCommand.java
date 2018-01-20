package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import by.epam.tc.conference.web.controller.command.impl.proposal.UpdateProposalCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateConferenceCommand extends AbstractCommand{
    private static final Logger logger = LogManager.getLogger(UpdateProposalCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private final ConferenceService conferenceService;

    public UpdateConferenceCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String conferenceIdString = request.getParameter(CONFERENCE_ID_PARAM);
        if (conferenceIdString == null) {
            return processInternalError(request, response);
        }
        try {
            Long conferenceId = Long.valueOf(conferenceIdString);
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute("conference", conference);
            return "conference-form";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

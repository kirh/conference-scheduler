package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowConferenceCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(ShowConferenceCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private final ConferenceService conferenceService;
    private final SectionService sectionService;

    public ShowConferenceCommand(ConferenceService conferenceService, SectionService sectionService) {
        this.conferenceService = conferenceService;
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idString = request.getParameter(CONFERENCE_ID_PARAM);
        if (idString == null) {
            return processPageNotFound(request, response);
        }
        String query;
        try {
            Long conferenceId = Long.valueOf(idString);
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute("conference", conference);
            List<Section> sections = sectionService.findSectionsByConferenceId(conferenceId);
            request.setAttribute("sections", sections);
            query = "conference";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }
}

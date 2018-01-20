package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessConferenceCommand extends AbstractCommand{
    private static final Logger logger = LogManager.getLogger(ProcessConferenceCommand.class);
    private final Builder<? extends Conference> conferenceBuilder;
    private final ConferenceService conferenceService;

    public ProcessConferenceCommand(Builder<? extends Conference> conferenceBuilder, ConferenceService conferenceService) {
        this.conferenceBuilder = conferenceBuilder;
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Conference conference = conferenceBuilder.build(request);
        try {
            Long conferenceId = conference.getId();
            if (conferenceId == null) {
                conferenceService.createConference(conference);
            } else {
                conferenceService.updateConference(conference);
            }
            return "redirect:/admin-dashboard";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

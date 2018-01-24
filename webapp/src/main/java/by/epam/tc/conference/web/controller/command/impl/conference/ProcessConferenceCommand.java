package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessConferenceCommand extends AbstractCommand{

    private static final Logger logger = LogManager.getLogger(ProcessConferenceCommand.class);
    private static final String REDIRECT_TO_DASHBOARD_QUERY = "redirect:/admin-dashboard";
    private final Builder<? extends Conference> conferenceBuilder;
    private final ConferenceService conferenceService;

    public ProcessConferenceCommand(Builder<? extends Conference> conferenceBuilder, ConferenceService conferenceService) {
        this.conferenceBuilder = conferenceBuilder;
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Conference conference = conferenceBuilder.build(request);
        try {
            String query;
            Long conferenceId = conference.getId();
            if (conferenceId == null) {
                conferenceService.createConference(conference);
                query = REDIRECT_TO_DASHBOARD_QUERY;
            } else {
                query = update(request, response, conference);
            }
            return logger.traceExit(query);
        } catch (InvalidEntityException | EntityNotFoundException e) {
            logger.debug("Failed to process conference {}", conference, e);
            return processBadRequest(request, response);
        } catch (ServiceException e) {
            logger.error("Failed to process conference {}" ,conference, e);
            return processInternalError(request, response);
        }
    }

    private String update(HttpServletRequest request, HttpServletResponse response, Conference conference) throws ServiceException {
        Long userId = getUserId(request);
        if (isOwner(conference, userId)) {
            conferenceService.updateConference(conference);
            return REDIRECT_TO_DASHBOARD_QUERY;
        }
        logger.debug("Failed to update conference id={}, user id={} is not the owner", conference.getId(), userId);
        return forbidRequest(request, response);
    }

    private boolean isOwner(Conference conference, long userId) throws ServiceException {
        Long conferenceId = conference.getId();
        Conference persistedConference = conferenceService.getConference(conferenceId);
        long administratorId = persistedConference.getAdministratorId();
        return administratorId == userId;
    }
}

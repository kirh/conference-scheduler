package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalDetailsService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.ErrorMessage;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ParticipantDashboardCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ParticipantDashboardCommand.class);
    private static final String USER_DASHBOARD_VIEW = "user-dashboard";

    private final ProposalDetailsService proposalDetailsService;

    public ParticipantDashboardCommand(ProposalDetailsService proposalService) {
        this.proposalDetailsService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.debug("Loading participant dashboard");
        UserPrincipal user = getUser(request);
        if (user.isAdmin()) {
            throw new CommandException("Page only for participants", HttpServletResponse.SC_FORBIDDEN,
                    ErrorMessage.FORBIDDEN);
        }
        try {
            Long userId = user.getId();
            List<ProposalDetails> proposals = proposalDetailsService.findProposalsDetailsByParticipantId(userId);
            request.setAttribute("proposals", proposals);
            return USER_DASHBOARD_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to load dashboard");
        }
    }
}

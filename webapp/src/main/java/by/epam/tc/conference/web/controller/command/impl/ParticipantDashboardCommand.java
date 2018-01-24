package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ParticipantDashboardCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ParticipantDashboardCommand.class);
    private final ProposalService proposalService;

    public ParticipantDashboardCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Long userId = getUserId(request);
        try {
            List<ProposalDetails> proposals = proposalService.findProposalsDetailsByParticipantId(userId);
            request.setAttribute("proposals", proposals);
           return logger.traceExit("user-dashboard");
        } catch (ServiceException e) {
            logger.error("Failed to load participant-dashboard", e);
            return processInternalError(request, response);
        }
    }
}

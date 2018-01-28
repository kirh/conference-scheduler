package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.ProposalDetailsService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ParticipantDashboardCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ParticipantDashboardCommand.class);

    private final ProposalDetailsService proposalDetailsService;

    public ParticipantDashboardCommand(ProposalDetailsService proposalService) {
        this.proposalDetailsService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            logger.debug("Loading dashboard");
            Long userId = getUserId(request);
            List<ProposalDetails> proposals = proposalDetailsService.findProposalsDetailsByParticipantId(userId);
            request.setAttribute("proposals", proposals);
            return logger.traceExit("user-dashboard");
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to load dashboard");
        }
    }
}

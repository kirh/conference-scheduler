package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.ErrorMessage;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateProposalStatusCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(UpdateProposalStatusCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private static final String PROPOSAL_STATUS_PARAM = "status";
    private final ProposalService proposalService;

    public UpdateProposalStatusCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long proposalId = parseIdParameter(request, PROPOSAL_ID_PARAM);
            ProposalStatus status = parseStatus(request);
            logger.debug("Update proposal id={} status to {}", proposalId, status);
            Long userId = getUserId(request);
            proposalService.updateStatus(proposalId, status, userId);
            return "redirect:/proposal?action=show&id=" + proposalId;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to update status");
        }
    }

    private ProposalStatus parseStatus(HttpServletRequest request) throws CommandException {
        try {
            String statusString = request.getParameter(PROPOSAL_STATUS_PARAM);
            return ProposalStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.BAD_REQUEST);
        }
    }
}

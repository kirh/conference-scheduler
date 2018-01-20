package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateProposalCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(UpdateProposalCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private final ProposalService proposalService;

    public UpdateProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idString = request.getParameter(PROPOSAL_ID_PARAM);
        String statusString = request.getParameter("status");
        if (idString == null || statusString == null) {
            return processBadRequest(request, response);
        }
        ProposalStatus status;
        Long id;
        try {
            id = Long.valueOf(idString);
            status = ProposalStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return processBadRequest(request, response);
        }

        try {
            proposalService.updateStatus(id, status);
            return "redirect:/proposal?action=show&id=" + id;
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

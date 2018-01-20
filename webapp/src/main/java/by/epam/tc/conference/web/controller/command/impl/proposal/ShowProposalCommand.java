package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowProposalCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(ShowProposalCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private final ProposalService proposalService;

    public ShowProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String proposalIdString = request.getParameter(PROPOSAL_ID_PARAM);
        if (proposalIdString == null) {
            return processBadRequest(request, response);
        }
        Long proposalId = Long.valueOf(proposalIdString);
        String query;
        try {
            Proposal proposal = proposalService.getProposal(proposalId);
            request.setAttribute("proposal", proposal);
            query = "proposal";
        } catch (ServiceException e) {
            logger.error("Error occurred in attempt to show proposal", e);
            query = processInternalError(request, response);
        }
        return query;
    }
}

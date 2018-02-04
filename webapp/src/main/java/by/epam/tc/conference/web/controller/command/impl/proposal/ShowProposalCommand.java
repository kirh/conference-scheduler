package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds proposal with specified id to request attribute and returns proposal view.
 */
public class ShowProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowProposalCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private static final String PROPOSAL_ATTRIBUTE = "proposal";
    private static final String PROPOSAL_VIEW = "proposal";
    private final ProposalService proposalService;

    public ShowProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long id = parseIdParameter(request, PROPOSAL_ID_PARAM);
            logger.debug("Show proposal id={}", id);
            Proposal proposal = proposalService.getProposal(id);
            request.setAttribute(PROPOSAL_ATTRIBUTE, proposal);
            return PROPOSAL_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to show proposal");
        }
    }
}

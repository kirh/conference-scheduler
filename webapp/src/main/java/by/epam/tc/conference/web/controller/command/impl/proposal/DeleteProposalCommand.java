package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.Dispatcher;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Delete proposal with id specified in request parameter.
 */
public class DeleteProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(DeleteProposalCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private final ProposalService proposalService;

    public DeleteProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long proposalId = parseIdParameter(request, PROPOSAL_ID_PARAM);
            logger.debug("Deleting proposal id={}", proposalId);
            Long userId = getUserId(request);
            proposalService.deleteProposal(proposalId, userId);
            return Dispatcher.SKIP;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to delete proposal");
        }
    }
}

package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowProposalCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private static final String PROPOSAL_ATTRIBUTE = "proposal";
    private final ProposalService proposalService;

    public ShowProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long id = parseIdParameter(request, PROPOSAL_ID_PARAM);
        if (id == null) {
            logger.debug("Failed to show proposal. Incorrect id");
            return processBadRequest(request, response);
        }

        String query;
        try {
            Proposal proposal = proposalService.getProposal(id);
            request.setAttribute(PROPOSAL_ATTRIBUTE, proposal);
            return  "proposal";
        } catch (EntityNotFoundException e) {
            logger.debug("Failed to show proposal id={}", id, e);
            return processPageNotFound(request, response);
        } catch (ServiceException e) {
            logger.error("Error occurred in attempt to show proposal", e);
            return processInternalError(request, response);
        }
    }
}

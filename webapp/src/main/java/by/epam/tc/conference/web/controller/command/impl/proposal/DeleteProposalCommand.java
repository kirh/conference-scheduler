package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(DeleteProposalCommand.class);
    private static final String PROPOSAL_ID_PARAM = "id";
    private static final String REDIRECT_USER_DASHBOARD = "redirect:/user-dashboard";
    private final ProposalService proposalService;

    public DeleteProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Long id = parseIdParameter(request, PROPOSAL_ID_PARAM);
        if (id == null) {
            logger.debug("Failed to delete proposal. Incorrect proposal id");
            return processBadRequest(request, response);
        }
        try {
            Proposal proposal = proposalService.getProposal(id);
            long participantId = proposal.getParticipantId();
            UserPrincipal user = getUser(request);
            long userId = user.getId();
            String query;
            if (participantId == userId) {
                proposalService.deleteProposal(id);
                query = REDIRECT_USER_DASHBOARD;
            } else {
                logger.debug("Failed to delete proposal id={}. Request forbidden. User id={} is not the owner", id);
                query = forbidRequest(request, response);
            }
            return query;
        } catch (ServiceException e) {
            logger.error("Failed to delete proposal id={}", id, e);
            return processInternalError(request, response);
        } catch (EntityNotFoundException e) {
            return REDIRECT_USER_DASHBOARD;
        }
    }
}

package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import by.epam.tc.conference.web.controller.command.impl.conference.ProcessConferenceCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProcessProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessConferenceCommand.class);
    private static final String REDIRECT_PARTICIPANT_DASHBOARD_QUERY = "redirect:/user-dashboard";
    private final ProposalService proposalService;
    private final Builder<? extends Proposal> builder;

    public ProcessProposalCommand(ProposalService proposalService, Builder<? extends Proposal> builder) {
        this.proposalService = proposalService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Proposal proposal = builder.build(request);
        String query;
        try {
            if (proposal.getId() == null) {
                proposalService.createProposal(proposal);
                query = REDIRECT_PARTICIPANT_DASHBOARD_QUERY;
            } else {
                query = update(request, response, proposal);
            }
            return logger.traceExit(query);
        } catch (InvalidEntityException | EntityNotFoundException e) {
            logger.debug("Failed to process proposal {}", proposal, e);
            return processBadRequest(request, response);
        } catch (ServiceException e) {
            logger.error("Internal Error. Failed to process proposal {}", proposal, e);
            return processInternalError(request, response);
        }
    }

    private String update(HttpServletRequest request, HttpServletResponse response, Proposal proposal) throws
            ServiceException, InvalidEntityException, EntityNotFoundException {
        Long userId = getUserId(request);
        if (isOwner(proposal, userId)) {
            proposalService.updateProposal(proposal);
            return REDIRECT_PARTICIPANT_DASHBOARD_QUERY;
        }
        logger.debug("Failed to update proposal id={}, user id={} is not the owner", proposal.getId(), userId);
        return forbidRequest(request, response);
    }

    private boolean isOwner(Proposal proposal, long userId) throws ServiceException, EntityNotFoundException {
        Long id = proposal.getId();
        Proposal persistedProposal = proposalService.getProposal(id);
        long participantId = persistedProposal.getParticipantId();
        return participantId == userId;
    }
}

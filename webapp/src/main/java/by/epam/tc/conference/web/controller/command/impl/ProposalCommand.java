package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.ProposalBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class ProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProposalCommand.class);

    private static final String PROPOSAL_ID_PARAM = "id";
    private static final String SECTION_ID_PARAM = "sectionId";

    private final ProposalService proposalService;
    private final Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions;

    public ProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;

        Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions = new HashMap<>();
        actions.put("show", this::showProposal);
        actions.put("update", this::updateStatus);
        actions.put("showAll", this::showProposals);
        actions.put("delete", this::deleteProposal);
        actions.put("process", this::processProposal);
        actions.put("create", (request, response) -> "proposal-form");
        this.actions = Collections.unmodifiableMap(actions);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String actionParameter = request.getParameter("action");
        BiFunction<HttpServletRequest, HttpServletResponse, String> action = actions.get(actionParameter);
        if (action == null) {
            return processPageNotFound(request, response);
        }
        return action.apply(request, response);
    }

    private String showProposal(HttpServletRequest request, HttpServletResponse response) {
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

    private String updateStatus(HttpServletRequest request, HttpServletResponse response) {
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

    private String showProposals(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter(SECTION_ID_PARAM);
        Long id = Long.valueOf(idString);
        String query;
        try {
            List<ProposalDetails> proposals = proposalService.findProposalsDetailsBySectionId(id);
            request.setAttribute("proposals", proposals);
            query = "section";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }

    private String processProposal(HttpServletRequest request, HttpServletResponse response) {
        Proposal proposal = new ProposalBuilder().build(request);
        try {
            if (proposal.getId() == null) {
                proposalService.createProposal(proposal);
            } else {
                proposalService.updateProposal(proposal);
            }
            return "redirect:/user-dashboard";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }

    private String deleteProposal(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        Long id = Long.valueOf(idString);
        try {
            proposalService.deleteProposal(id);
            return "redirect:";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

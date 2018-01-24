package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowSectionProposalsCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowSectionProposalsCommand.class);
    private static final String SECTION_ID_PARAM = "sectionId";
    private static final String PROPOSALS_ATTRIBUTE = "proposals";
    private final ProposalService proposalService;

    public ShowSectionProposalsCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long sectionId = parseIdParameter(request, SECTION_ID_PARAM);
        if (sectionId == null) {
            logger.debug("Failed to show proposals for section. Incorrect section id");
            return processBadRequest(request, response);
        }
        try {
            List<ProposalDetails> proposals = proposalService.findProposalsDetailsBySectionId(sectionId);
            request.setAttribute(PROPOSALS_ATTRIBUTE, proposals);
            return  "section";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

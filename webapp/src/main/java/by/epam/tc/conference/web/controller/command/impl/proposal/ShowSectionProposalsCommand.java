package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.ProposalDetailsService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
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
    private static final String SECTION_VIEW = "section";

    private final ProposalDetailsService proposalDetailsService;

    public ShowSectionProposalsCommand(ProposalDetailsService proposalDetailsService) {
        this.proposalDetailsService = proposalDetailsService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long sectionId = parseIdParameter(request, SECTION_ID_PARAM);
            logger.debug("Show section id={}", sectionId);
            List<ProposalDetails> proposals = proposalDetailsService.findProposalsDetailsBySectionId(sectionId);
            request.setAttribute(PROPOSALS_ATTRIBUTE, proposals);
            return SECTION_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to show section");
        }
    }
}

package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import javax.servlet.http.HttpServletRequest;

public class ProposalBuilder extends AbstractBuilder<Proposal> {

    private static final java.lang.String ID_PARAM = "id";
    private static final java.lang.String TITLE_PARAM = "title";
    private static final java.lang.String DESCRIPTION_PARAM = "description";
    private static final java.lang.String SECTION_ID_PARAM = "sectionId";

    @Override
    public Proposal build(HttpServletRequest request) {
        java.lang.String idString = request.getParameter(ID_PARAM);
        Long id = parseId(idString);
        java.lang.String title = request.getParameter(TITLE_PARAM);
        java.lang.String description = request.getParameter(DESCRIPTION_PARAM);
        java.lang.String sectionIdString = request.getParameter(SECTION_ID_PARAM);
        Long sectionId = Long.valueOf(sectionIdString);
        Long participantId = getUserId(request);
        return new Proposal(id, title, description, sectionId, participantId, ProposalStatus.PENDING);
    }
}

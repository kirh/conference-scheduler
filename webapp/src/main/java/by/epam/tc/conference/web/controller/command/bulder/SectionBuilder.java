package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Section;

import javax.servlet.http.HttpServletRequest;

public class SectionBuilder extends AbstractBuilder<Section> {

    private static final String ID_PARAM = "id";
    private static final String TOPIC_PARAM = "topic";
    private static final String CONFERENCE_ID_PARAM = "conferenceId";

    @Override
    public Section build(HttpServletRequest request) {
        String idString = request.getParameter(ID_PARAM);
        Long id = parseId(idString);
        String topic = request.getParameter(TOPIC_PARAM);
        String conferenceIdString = request.getParameter(CONFERENCE_ID_PARAM);
        Long conferenceId = parseId(conferenceIdString);
        return new Section(id, topic, conferenceId);
    }
}

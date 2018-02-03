package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Question;

import javax.servlet.http.HttpServletRequest;

public class QuestionBuilder extends AbstractBuilder<Question> {

    private static final String CONFERENCE_ID_PARAM = "conferenceId";
    private static final String TITLE_PARAM = "title";

    @Override
    public Question build(HttpServletRequest request) {
        String title = request.getParameter(TITLE_PARAM);
        String conferenceIdString = request.getParameter(CONFERENCE_ID_PARAM);
        Long conferenceId = parseId(conferenceIdString);
        Long userId = getUserId(request);
        return new Question(title, userId, conferenceId);
    }
}

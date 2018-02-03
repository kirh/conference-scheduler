package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Message;

import javax.servlet.http.HttpServletRequest;

public class MessageBuilder extends AbstractBuilder<Message> {

    private static final String TEXT_PARAM = "text";
    private static final String QUESTION_ID_PARAM = "questionId";

    @Override
    public Message build(HttpServletRequest request) {
        String text = request.getParameter(TEXT_PARAM);
        String questionIdString = request.getParameter(QUESTION_ID_PARAM);
        Long questionId = parseId(questionIdString);
        Long userId = getUserId(request);
        return new Message(text, questionId, userId);
    }
}

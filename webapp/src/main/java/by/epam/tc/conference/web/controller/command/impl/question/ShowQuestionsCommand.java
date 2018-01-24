package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.dto.QuestionDetails;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionsCommand extends AbstractCommand {

    private final QuestionService questionService;

    public ShowQuestionsCommand(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserPrincipal user = getUser(request);
            Long id = user.getId();
            List<QuestionDetails> questions;
            if (user.isAdmin()) {
                questions = questionService.findQuestionsByAdministratorId(id);
            } else {
                questions = questionService.findQuestionsByParticipantId(id);
            }
            request.setAttribute("questions", questions);
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
        return "questions";
    }
}

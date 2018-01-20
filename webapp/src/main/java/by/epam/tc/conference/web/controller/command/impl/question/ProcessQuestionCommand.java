package by.epam.tc.conference.web.controller.command.impl.question;

import by.epam.tc.conference.entity.Message;
import by.epam.tc.conference.entity.Question;
import by.epam.tc.conference.services.QuestionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessQuestionCommand extends AbstractCommand {
    private final QuestionService questionService;
    private final Builder<? extends Question> questionBuilder;
    private final Builder<? extends Message> messageBuilder;

    public ProcessQuestionCommand(QuestionService questionService, Builder<? extends Question> questionBuilder,
                                  Builder<? extends Message> messageBuilder) {
        this.questionService = questionService;
        this.questionBuilder = questionBuilder;
        this.messageBuilder = messageBuilder;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Question question = questionBuilder.build(request);
        Message message = messageBuilder.build(request);
        try {
            questionService.createQuestion(question, message);
            return "redirect:/conference?action=show&id=" + question.getConferenceId();
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}

package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.web.controller.ErrorMessage;
import by.epam.tc.conference.web.controller.Languages;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class);
    private static final String LANGUAGE_PARAM = "lang";
    private static final String REDIRECT_PREVIOUS_PAGE = "redirect:";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.traceEntry();
        String language = request.getParameter(LANGUAGE_PARAM);
        if (!Languages.contains(language)) {
            logger.debug("Language '{}' not supported. Bad request", language);
            String message = "Language " + language + " not supported";
            throw new CommandException(message, HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.BAD_REQUEST);
        }
        Locale locale = new Locale(language);
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.LOCALE, locale);
        logger.debug("Language switched to '{}'", language);
        return REDIRECT_PREVIOUS_PAGE;
    }
}

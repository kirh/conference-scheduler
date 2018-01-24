package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.web.controller.Languages;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class);
    private static final String LANGUAGE_PARAM = "lang";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        String language = request.getParameter(LANGUAGE_PARAM);
        if (!Languages.contains(language)) {
            logger.debug("Language '{}' not supported. Bad request", language);
            return processBadRequest(request, response);
        }
        Locale locale = new Locale(language);
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.LOCALE, locale);
        logger.debug("Language switched to '{}'", language);
        return "redirect:";
    }
}

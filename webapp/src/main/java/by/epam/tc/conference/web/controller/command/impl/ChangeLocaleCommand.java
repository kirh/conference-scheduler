package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.Languages;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    private static final String LANGUAGE_PARAM = "lang";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String language = request.getParameter(LANGUAGE_PARAM);
        if (Languages.contains(language)) {
            Locale locale = new Locale(language);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.LOCALE, locale);
        }
        return "redirect:";
    }
}

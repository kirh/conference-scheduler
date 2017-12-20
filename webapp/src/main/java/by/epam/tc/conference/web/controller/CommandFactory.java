package by.epam.tc.conference.web.controller;

import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.LoginCommand;
import by.epam.tc.conference.web.controller.command.UnknownCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandFactory {

    public static Command create(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if ("login".equals(action)) {
            return new LoginCommand();
        } else {
            return new UnknownCommand();
        }
    }
}

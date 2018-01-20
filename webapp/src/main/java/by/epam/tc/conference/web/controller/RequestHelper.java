package by.epam.tc.conference.web.controller;

import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandFactory;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    private final CommandFactory commandFactory = CommandFactory.getInstance();

    public Command getCommand(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String action = request.getParameter("action");
        String pathWithoutLeadingSlash = pathInfo.substring(1);
        String command = action == null ? pathWithoutLeadingSlash : pathWithoutLeadingSlash + "?" + action;
        return commandFactory.getCommand(command);
    }
}
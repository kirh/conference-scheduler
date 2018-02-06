package by.epam.tc.conference.web.controller;

import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.CommandFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Returns command appropriate for request
 */
public class RequestHelper {

    private final CommandFactory commandFactory = CommandFactory.getInstance();

    /**
     * Returns command appropriate for request path and parameters
     * @param request to process
     * @return command appropriate for request
     * @throws CommandException when command not found
     */
    public Command getCommand(HttpServletRequest request) throws CommandException {
        String path = request.getServletPath();
        String action = request.getParameter("action");
        String command = action == null ? path : path + "?action=" + action;
        return commandFactory.getCommand(command);
    }
}

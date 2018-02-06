package by.epam.tc.conference.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contains logic to process user request.
 */
public interface Command {

    /**
     * Processes request and specifies {@link by.epam.tc.conference.web.controller.Dispatcher} query string.
     * @param request object that contains the request the client has made of the servlet
     * @param response object that contains the response the servlet sends to the client
     * @return {@link by.epam.tc.conference.web.controller.Dispatcher} query string
     * @throws CommandException when error during processing occurs
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}

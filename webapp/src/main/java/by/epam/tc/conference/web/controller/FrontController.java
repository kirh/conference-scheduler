package by.epam.tc.conference.web.controller;

import by.epam.tc.conference.web.ErrorMessage;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * General application controller. Processes all GET POST DELETE HTTP requests.
 */
public class FrontController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(FrontController.class);
    private final Dispatcher dispatcher = new Dispatcher();
    private final RequestHelper helper = new RequestHelper();

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logRequest(request);
            Command command = helper.getCommand(request);
            String query = command.execute(request, response);
            dispatcher.dispatch(query, request, response);
        } catch (CommandException e) {
            String errorMessage = e.getErrorMessage();
            int errorCode = e.getErrorCode();
            response.sendError(errorCode, errorMessage);
            logger.error("Error code = {}", errorCode, e);
        } catch (Exception e) {
            logger.error("Error code = 500", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL);
        }
    }

    private void logRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        logger.debug("{}{}{}", method, path, queryString == null ? "" : "?" + queryString);
    }
}


package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.web.controller.ErrorMessage;
import by.epam.tc.conference.web.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UnknownCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        request.setAttribute(RequestDispatcher.ERROR_MESSAGE, ErrorMessage.NOT_FOUND);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "error";
    }
}

package by.epam.tc.conference.web.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(UnknownCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        return processPageNotFound(request, response);
    }
}

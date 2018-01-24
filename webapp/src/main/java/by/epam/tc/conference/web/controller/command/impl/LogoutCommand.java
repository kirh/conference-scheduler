package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.web.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        HttpSession session = request.getSession();
        session.invalidate();
        logger.debug("Logged out");
        return "redirect:/login";
    }
}

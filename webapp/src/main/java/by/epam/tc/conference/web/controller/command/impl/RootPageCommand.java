package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RootPageCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(RootPageCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        UserPrincipal user = getUser(request);
        if (user.isAdmin()) {
            return logger.traceExit("forward:/admin-dashboard");
        }
        return logger.traceExit("forward:/user-dashboard");
    }
}

package by.epam.tc.conference.web.controller;

import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.CommandFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private final Dispatcher dispatcher = new Dispatcher();

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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String commandName = path.substring(1);
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(commandName);
        try {
            String query = command.execute(request, response);
            dispatcher.dispatch(query, request, response);
        } catch (CommandException e) {
            throw new ServletException(e);
        }
    }
}

package by.epam.tc.conference.web.controller;

import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

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
        Command command = CommandFactory.create(request, response);
        try {
            String view = command.execute(request, response);
            forward(view, request, response);
        } catch (CommandException e) {
            throw new ServletException(e);
        }
    }

    private void forward(String view, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/" + view);
        dispatcher.forward(request, response);
    }
}

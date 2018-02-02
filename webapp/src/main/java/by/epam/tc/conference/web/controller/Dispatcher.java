package by.epam.tc.conference.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *Based on incoming query it can redirect, forward to another page or render specified view.
 *For more information see {@link #dispatch} method
 */
public class Dispatcher {

    public static final String REDIRECT_PREFIX = "redirect:";
    public static final String FORWARD_PREFIX = "forward:";
    private static final String VIEW_PATH_PREFIX = "/WEB-INF/jsp/";
    private static final String VIEW_PATH_POSTFIX = ".jsp";
    private static final String CONTROLLER_PREFIX = "/controller";

    /**
     * Based on incoming query it can redirect, forward to another page or render specified view.<br>
     * Use in your query: <br>
     * {@link #REDIRECT_PREFIX} to redirect<br>
     * {@link #FORWARD_PREFIX} to forward<br>
     * If prefix is followed by '/' dispatcher interprets it as relative to the servlet container root, otherwise
     * relative to current uri.
     * When prefix not specified then it will render view.
     *
     * @param query to proceed
     * @param request object that contains the request the client has made of the servlet
     * @param response object that contains the response the servlet sends to the client
     * @throws IOException if the target resource throws this exception
     * @throws ServletException if the target resource throws this exception
     * @throws IllegalArgumentException  if the response was already committed
     */
    public void dispatch(String query, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (query.startsWith(REDIRECT_PREFIX)) {
            redirect(query, request, response);
        } else if (query.startsWith(FORWARD_PREFIX)) {
            String pathInfo = query.replace(FORWARD_PREFIX, "");
            String path = CONTROLLER_PREFIX + pathInfo;
            forward(path, request, response);
        } else {
            String viewPath = VIEW_PATH_PREFIX + query + VIEW_PATH_POSTFIX;
            forward(viewPath, request, response);
        }
    }

    private void redirect(String query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = query.replace(REDIRECT_PREFIX, "");
        if (!path.isEmpty()) {
            response.sendRedirect(path);
        } else {
            String referer = request.getHeader("referer");
            response.sendRedirect(referer);
        }
    }

    private void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

}

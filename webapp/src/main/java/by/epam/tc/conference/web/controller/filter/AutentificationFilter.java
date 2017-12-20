package by.epam.tc.conference.web.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AutentificationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        Object user = session.getAttribute("user");
        if (user == null && !"/login.jsp".equals(((HttpServletRequest) request).getServletPath())) {
            ((HttpServletResponse) response).sendRedirect("login.jsp");
        }
    }

        @Override
    public void destroy() {

    }
}

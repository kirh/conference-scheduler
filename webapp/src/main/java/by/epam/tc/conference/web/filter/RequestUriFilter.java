package by.epam.tc.conference.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Forwards all requests to front controller
 */
public class RequestUriFilter implements Filter {

    private static final String STATIC_URI_PREFIX = "/static";
    private static final String CONTROLLER_URI_PREFIX = "/controller";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = uri.substring(contextPath.length());
        if (path.startsWith(STATIC_URI_PREFIX)) {
            chain.doFilter(request, response);

        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(CONTROLLER_URI_PREFIX + path);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

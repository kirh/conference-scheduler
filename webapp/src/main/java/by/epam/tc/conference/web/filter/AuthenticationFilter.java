package by.epam.tc.conference.web.filter;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.controller.SessionAttribute;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {

    private static final String LOGIN_PAGE = "/login";
    private static final String SIGN_UP_PAGE = "/register";
    private static final String STATIC_PREFIX = "/static/";
    public static final String CHANGE_LOCALE = "/change-locale";
    private Set<String> uriWhiteSet;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        HashSet<String> set = new HashSet<>();
        set.add(LOGIN_PAGE);
        set.add(SIGN_UP_PAGE);
        set.add(CHANGE_LOCALE);
        uriWhiteSet = Collections.unmodifiableSet(set);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        UserPrincipal user = (UserPrincipal) session.getAttribute(SessionAttribute.USER_PRINCIPAL);
        String uri = getUri(httpRequest);

        if (user == null && !uriWhiteSet.contains(uri) && !uri.startsWith(STATIC_PREFIX)) {
            ((HttpServletResponse) response).sendRedirect(LOGIN_PAGE);
        } else {
            if (user != null) {
                populateThreadContext(user);
            }
            chain.doFilter(request, response);
            ThreadContext.clearMap();
        }
    }

    private void populateThreadContext(UserPrincipal user) {
        String idString = String.valueOf(user.getId());
        ThreadContext.put("userId", idString);
        ThreadContext.put("username", user.getUsername());
        String role = user.isAdmin() ? "admin" : "participant";
        ThreadContext.put("role", role);

    }

    private String getUri(HttpServletRequest request) {
        try {
            String contextPathEncoded = request.getContextPath();
            String contextPath = URLDecoder.decode(contextPathEncoded, StandardCharsets.UTF_8.displayName());
            String uriEncoded = request.getRequestURI();
            String uri = URLDecoder.decode(uriEncoded, StandardCharsets.UTF_8.displayName());
            return uri.substring(contextPath.length());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void destroy() {

    }
}

package by.epam.tc.conference.web.filter;

import by.epam.tc.conference.web.controller.Languages;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Checks user for locale. If locales wasn't specified, then attempts to resolve it.
 */
public class LocaleFilter implements Filter {

    private static final String ATTRIBUTE_LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Object localeAttribute = session.getAttribute(ATTRIBUTE_LOCALE);

        if (localeAttribute == null) {
            Enumeration<Locale> locales = request.getLocales();
            Locale locale = resolveLocale(locales);
            session.setAttribute(ATTRIBUTE_LOCALE, locale);
        }
        chain.doFilter(request, response);
    }

    private Locale resolveLocale(Enumeration<Locale> acceptedLocales) {
        String language = null;
        while (acceptedLocales.hasMoreElements() && language == null) {
            Locale locale = acceptedLocales.nextElement();
            String acceptedLanguage = locale.getLanguage();
            if (Languages.contains(acceptedLanguage)) {
                language = acceptedLanguage;
            }
        }
        if (language == null) {
            language = Languages.DEFAULT.getCode();
        }
        return Locale.forLanguageTag(language);
    }

    @Override
    public void destroy() {

    }
}

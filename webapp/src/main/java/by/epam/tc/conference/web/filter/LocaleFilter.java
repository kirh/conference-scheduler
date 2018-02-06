package by.epam.tc.conference.web.filter;

import by.epam.tc.conference.web.Language;
import by.epam.tc.conference.web.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Checks user for locale. If locales wasn't specified, then attempts to resolve it based on request locales.
 */
public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Object localeAttribute = session.getAttribute(SessionAttribute.LOCALE);

        if (localeAttribute == null) {
            Enumeration<Locale> locales = request.getLocales();
            Locale locale = resolveLocale(locales);
            session.setAttribute(SessionAttribute.LOCALE, locale);
        }
        chain.doFilter(request, response);
    }

    private Locale resolveLocale(Enumeration<Locale> userLocales) {
        Language language = null;
        while (userLocales.hasMoreElements()) {
            Locale locale = userLocales.nextElement();
            String acceptedLanguageTag = locale.toLanguageTag();
            if (Language.contains(acceptedLanguageTag)) {
                language = Language.resolve(locale);
                break;
            }

        }
        return language == null ? Language.DEFAULT.getLocale() : language.getLocale();
    }

    @Override
    public void destroy() {

    }
}

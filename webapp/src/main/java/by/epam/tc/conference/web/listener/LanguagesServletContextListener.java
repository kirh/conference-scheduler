package by.epam.tc.conference.web.listener;

import by.epam.tc.conference.web.Language;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.List;

/**
 * Populates context with application languages
 */
public class LanguagesServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        Language[] languages = Language.values();
        List<Language> languageList = Arrays.asList(languages);
        servletContext.setAttribute("languages", languageList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

package by.epam.tc.conference.web.listener;

import by.epam.tc.conference.web.controller.Languages;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.List;

/**
 * Populates context with application languages
 */
public class LanguagesServletContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        Languages[] languages = Languages.values();
        List<Languages> languagesList = Arrays.asList(languages);
        servletContext.setAttribute("languages", languagesList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

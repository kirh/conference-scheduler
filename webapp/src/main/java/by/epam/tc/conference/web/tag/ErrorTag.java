package by.epam.tc.conference.web.tag;

import by.epam.tc.conference.web.controller.ErrorMessage;
import by.epam.tc.conference.web.controller.SessionAttribute;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Tag to display errors
 */
public class ErrorTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String error = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        initLocalizationContext(request);
        String errorMessage;
        if (error != null) {
            errorMessage = LocaleSupport.getLocalizedMessage(pageContext, error);
            if (errorMessage.startsWith("???")) {
                errorMessage = LocaleSupport.getLocalizedMessage(pageContext, ErrorMessage.INTERNAL);
            }
        } else {
            errorMessage = LocaleSupport.getLocalizedMessage(pageContext, ErrorMessage.INTERNAL);
        }

        JspWriter out = pageContext.getOut();
        try {
            out.write("<span class=\"error\">");
            out.write(errorMessage);
            out.write("</span>");
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspTagException(e.getMessage(), e);
        }
    }

    private void initLocalizationContext(HttpServletRequest request) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(SessionAttribute.LOCALE);
        LocalizationContext localizationContext = new LocalizationContext(bundle, locale);
        Config.set(pageContext, Config.FMT_LOCALIZATION_CONTEXT, localizationContext, PageContext.REQUEST_SCOPE);
    }
}

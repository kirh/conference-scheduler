package by.epam.tc.conference.web.taglib;

import by.epam.tc.conference.web.ErrorMessage;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Tag to display errors.
 */
public class ErrorTag extends TagSupport {

    private static final String I18N_BUNDLE_BASENAME = "i18n";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String error = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        String errorMessage;
        if (error != null) {
            errorMessage = LocaleSupport.getLocalizedMessage(pageContext, error, I18N_BUNDLE_BASENAME);
            if (errorMessage.startsWith("???")) {
                errorMessage = LocaleSupport.getLocalizedMessage(pageContext, ErrorMessage.INTERNAL, I18N_BUNDLE_BASENAME);
            }
        } else {
            errorMessage = LocaleSupport.getLocalizedMessage(pageContext, ErrorMessage.INTERNAL, I18N_BUNDLE_BASENAME);
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
}

package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Identifiable;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.controller.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractBuilder<T extends Identifiable> implements Builder<T> {

    protected Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserPrincipal user = (UserPrincipal) session.getAttribute(SessionAttribute.USER_PRINCIPAL);
        return user.getId();
    }

    protected Long parseId(String idString) {
        if (idString == null || idString.isEmpty()) {
            return null;
        }
        return Long.valueOf(idString);
    }
}

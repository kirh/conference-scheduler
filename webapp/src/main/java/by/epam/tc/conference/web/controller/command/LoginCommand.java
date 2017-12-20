package by.epam.tc.conference.web.controller.command;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.DaoFactory;
import by.epam.tc.conference.dao.sql.UserDao;
import by.epam.tc.conference.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final String USER_PARAM_NAME = "user";
    private static final String PASSWORD_PARAM_NAME = "password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String username = request.getParameter(USER_PARAM_NAME);
        String password = request.getParameter(PASSWORD_PARAM_NAME);
        DaoFactory daoFactory = DaoFactory.getInstanse();
        try {
            UserDao userDao = daoFactory.getUserDao();
            Optional<User> user = userDao.findByUserNameAndPassword(username, password);
            String view;
            if (user.isPresent()) {
                User user1 = user.get();
                HttpSession session = request.getSession();
                session.setAttribute("user", user1);
                view =  "redirect:/?action=dashboard";
            } else {
                view = "redirect:/login.jsp";
            }
            return view;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}

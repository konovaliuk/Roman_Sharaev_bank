package roman.ironbank.controller.commands.commandImpl;


import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandUserSignUp implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        //todo Ask: where I should to put logger instance
        String page = Pages.USER_CABINET;

        String nameParam = request.getParameter("name");
        String emailParam = request.getParameter("email");
        String passParam = request.getParameter("pass");


        UserDao userDao = new UserDao();
        User user = new User(nameParam, emailParam, passParam);

        if(userDao.findByEmail(emailParam) == null) {
            userDao.create(user);

            sessionLogOut(request);
            HttpSession session = request.getSession();

            user.clear();
            session.setAttribute("user", user);
        }
        else {
            page = Pages.USER_SIGN_UP;
            String errorMessage = "email has already been registered";
            request.setAttribute("message", errorMessage);
        }

        return page;
    }

    private void sessionLogOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }

}

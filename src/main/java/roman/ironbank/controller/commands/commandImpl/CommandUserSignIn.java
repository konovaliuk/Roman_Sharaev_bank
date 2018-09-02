package roman.ironbank.controller.commands.commandImpl;


import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.service.BCrypt;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandUserSignIn implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.USER_CABINET;

        sessionLogOut(request);

        String emailParam = request.getParameter("email");
        String passParam = request.getParameter("password");

        HttpSession session = request.getSession();

        UserDao userDao = new UserDao();
        User user = userDao.findByEmail(emailParam);

        if(user == null) {
            page = Pages.INDEX;
            request.setAttribute("message", "wrong email or password");
            return page;
        }
        else {
            String passDb = user.getPass();
//            if(BCrypt.checkpw(passParam, passDb))
                if (true){
//            session.setAttribute("user_name", user.getName());
                user.clear();
                session.setAttribute("user", user);
            } else {
                page = Pages.INDEX;
                request.setAttribute("message", "wrong email or password");
            }
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

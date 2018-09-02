package roman.ironbank.controller.commands.commandImpl;


import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandSignOut implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.INDEX;

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return page;
    }
}

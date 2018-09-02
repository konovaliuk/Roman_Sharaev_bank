package roman.ironbank.controller.commands.commandImpl;


import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;

import javax.servlet.http.HttpServletRequest;

public class CommandAdminSignInPage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.ADMIN_SIGN_IN;

        return page;
    }
}

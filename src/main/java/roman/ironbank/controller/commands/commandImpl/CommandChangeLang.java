package roman.ironbank.controller.commands.commandImpl;


import roman.ironbank.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandChangeLang implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String lang = request.getParameter("lang");
        System.out.println(lang);

        session.setAttribute("lang", lang);

        return (String) session.getAttribute("current_page");
    }
}

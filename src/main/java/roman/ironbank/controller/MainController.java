package roman.ironbank.controller;


import org.apache.log4j.Logger;
import roman.ironbank.controller.commands.ActionFactory;
import roman.ironbank.controller.commands.Command;
import roman.ironbank.model.dao.connection.ConnectionPool;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
@WebServlet(name = "controller", urlPatterns = "/controller")
public class MainController extends HttpServlet {
    private static final Logger loger = Logger.getLogger(MainController.class);
    private String encoding;
    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        encoding = config.getInitParameter("PARAMETER_ENCODING");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (encoding != null) {
            System.out.println("encoding is null. UTF-8 fixing ...");
            req.setCharacterEncoding(encoding);
        }

        String page;
        ActionFactory action = new ActionFactory();
        Command command = action.defineCommand(req);
        page = command.execute(req);

        if (page != null) {
            req.getRequestDispatcher("WEB-INF/jsp/" + page).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

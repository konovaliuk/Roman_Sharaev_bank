package roman.ironbank.controller.commands.commandImpl;

import org.apache.log4j.Logger;
import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.NewAccountRequest;
import roman.ironbank.domain.account.Transaction;
import roman.ironbank.domain.service.BCrypt;
import roman.ironbank.domain.users.Admin;
import roman.ironbank.model.dao.impl.AccountDao;
import roman.ironbank.model.dao.impl.AdminDao;
import roman.ironbank.model.dao.impl.NewAccountRequestDao;
import roman.ironbank.model.dao.impl.TransactionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

public class CommandAdminSignIn implements Command {

    Logger logger = Logger.getLogger(CommandAdminSignIn.class.getName());
    HttpSession session;

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        sessionLogOut(request);

        if(authorizeAdmin(request)) {
            page = Pages.ADMIN_CABINET;
            setParams(request, getAllNotConfirmedRequests(), getAllActiveAccounts(), getAllTransactions());
        }
        else {
            page = Pages.ADMIN_SIGN_IN;
            logger.info("Wrong email or password.");
            request.setAttribute("message", "Wrong email or password.");
        }

        return page;
    }

    private boolean authorizeAdmin(HttpServletRequest request) {
        sessionLogOut(request);

        String emailParam = request.getParameter("email");
        String passParam = request.getParameter("password");

        session = request.getSession();

        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.findByEmail(emailParam);

        if(admin == null) {
            return false;
        }
        else {
            String passDb = admin.getPass();
//            if(BCrypt.checkpw(passParam, passDb)) {
            if(true){
                admin.clear();
                session.setAttribute("admin", admin);
                return true;
            } else {
                return false;
            }
        }
    }

    private LinkedList<NewAccountRequest> getAllNotConfirmedRequests() {
        NewAccountRequestDao dao = new NewAccountRequestDao();
        LinkedList<NewAccountRequest> requests = dao.findAllNotConfirmed();
//        if(requests)
        return requests;
    }

    private void sessionLogOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }

    private LinkedList<Account> getAllActiveAccounts() {
        AccountDao accountDao = new AccountDao();
        return accountDao.findAll();
    }

    private LinkedList<Transaction> getAllTransactions() {
        TransactionDao transactionDao = new TransactionDao();
        return transactionDao.findAll();
    }

    private void setParams(HttpServletRequest request, LinkedList<NewAccountRequest> newAccountRequests, LinkedList<Account> activeAccounts, LinkedList<Transaction> transactions) {
        request.setAttribute("requests", newAccountRequests);
        request.setAttribute("active_accounts", activeAccounts);
        request.setAttribute("all_transactions", transactions);
    }
}

package roman.ironbank.controller.commands.commandImpl;

import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.account.Account;
import roman.ironbank.controller.commands.Command;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.service.log.FileLogger;
import roman.ironbank.domain.service.log.Logger;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.AccountDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

public class CommandCreditAccount implements Command {

    User user;
    AccountType accountType = AccountType.CREDIT;
    Account account;
    AccountDao accountDao;
    HttpSession session;
    Logger logger = new FileLogger();
    LinkedList<Account> accounts;

    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.CREDIT_ACCOUNT;

//        HttpSession session = request.getSession();

        init(request);
        getAccountInfo();
        setParams(request);


        return page;
    }

    private void init( HttpServletRequest request) {
        session = request.getSession();
        user = (User) session.getAttribute("user");

        if(user == null) {
            logger.log("Error! Empty param 'user'");
            request.setAttribute("message", "Error! Empty param 'user'. Please reenter.");
            return;
        }

        accountDao = new AccountDao();

    }

    private void getAccountInfo() {
        accounts = accountDao.findByUserAndAccountType(user, AccountType.CREDIT);
    }

    private void setParams(HttpServletRequest request) {
        session.setAttribute("account_type", "credit");
        session.setAttribute("credit_accounts", accounts);

    }
}

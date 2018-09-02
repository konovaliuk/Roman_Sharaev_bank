package roman.ironbank.controller.commands.commandImpl;

import org.apache.log4j.Logger;
import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.AccountDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

public class CommandDepositAccount implements Command {
    private static final Logger logger = Logger.getLogger(CommandDepositAccount.class.getName());

    private User user;
    private AccountType accountType = AccountType.DEPOSIT;
    private AccountDao accountDao;
    private HttpSession session;
    private LinkedList<Account> accounts;

    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.DEPOSIT_ACCOUNT;

//        HttpSession session = request.getSession();
//        session.setAttribute("account_type", "deposit");

        init(request);
        getAccountInfo();
        setParams(request);

        return page;
    }

    private void init(HttpServletRequest request) {
        session = request.getSession();
        user = (User) session.getAttribute("user");

        if(user == null) {
            logger.error("Error! Empty param 'user'");
            request.setAttribute("message", "Error! Empty param 'user'. Please reenter.");
            return;
        }

        accountDao = new AccountDao();

    }

    private void getAccountInfo() {
        accounts = accountDao.findByUserAndAccountType(user, AccountType.DEPOSIT);
    }

    private void setParams(HttpServletRequest request) {
        session.setAttribute("account_type", "deposit");
        session.setAttribute("deposit_accounts", accounts);

    }
}

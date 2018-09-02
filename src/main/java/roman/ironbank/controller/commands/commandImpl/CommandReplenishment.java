package roman.ironbank.controller.commands.commandImpl;


import org.apache.log4j.Logger;
import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.bank.Operator;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.AccountDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.LinkedList;

public class CommandReplenishment implements Command {
    private static Logger logger = Logger.getLogger(CommandReplenishment.class.getName());

    int accountId;
    double sum;
    HttpSession session;
    User user;
    private AccountType accountType;
    AccountDao accountDao;
    LinkedList<Account> accounts;

    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.DEPOSIT_ACCOUNT;

        if(collectParams(request) & init(request)) {
            if(operation()) {
                getAccountInfo();
                setParams(request);
            } else {
                request.setAttribute("message", "Error! Replenish was not successful.");
            }
        }

        return page;
    }

    private boolean init(HttpServletRequest request) {
        try {


            session = request.getSession();
            user = (User) session.getAttribute("user");
            accountType = AccountType.DEPOSIT;
            accountDao = new AccountDao();
            accounts = new LinkedList<>();


            if (user == null) {
                logger.error("Error! Empty param 'user'");
                request.setAttribute("message", "Error! Empty param 'user'. Please reenter.");
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private boolean collectParams(HttpServletRequest request) {
        boolean result = false;

        String accountIdParam = request.getParameter("account_id");
//        System.out.println("accountIdParam: " + accountIdParam);
        String sumParam = request.getParameter("sum");
//        System.out.println("sumParam: " + sumParam);

        try {
            accountId = Integer.parseInt(accountIdParam);
            sum = Double.parseDouble(sumParam);

//            System.out.println("accountId: " + accountId);
//            System.out.println("sum: " + sumParam);

            result = true;
        } catch (NullPointerException nullEx) {
            logger.error(nullEx.getMessage());
        } catch (NumberFormatException numbEx) {
            logger.error(numbEx.getMessage());
        }

        return result;
    }

    private boolean operation() {
        Operator operator = Operator.getInstance();
        boolean result = false;
        try {
            result = operator.pay(accountId, sum);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    private void getAccountInfo() {
        accounts = accountDao.findByUserAndAccountType(user, accountType);
    }


    private void setParams(HttpServletRequest request) {
        request.setAttribute("message", "Account " + accountId + " was successfully replenished to the amount of " + sum + ".");
        session.setAttribute("deposit_accounts", accounts);
    }
}

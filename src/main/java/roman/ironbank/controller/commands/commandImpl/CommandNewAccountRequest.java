package roman.ironbank.controller.commands.commandImpl;



import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.account.NewAccountRequest;
import roman.ironbank.domain.service.log.FileLogger;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.AccountDao;
import roman.ironbank.model.dao.impl.NewAccountRequestDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

public class CommandNewAccountRequest implements Command {
    FileLogger logger = new FileLogger();

    String page = Pages.USER_CABINET;

    HttpSession session;

    String accountTypeParam;
    AccountType type;
    private User user;

    double rate = 0.0;
    double creditLimit = 0.0;



    AccountDao accountDao;

    @Override
    public String execute(HttpServletRequest request) {

        collectParams(request);

        if(checkParamsAndReport(request)) {

            if(checkAndReport(type, user, request)) {
                NewAccountRequestDao dao = new NewAccountRequestDao();
                dao.create(new NewAccountRequest(type, user, rate, creditLimit));

                setAttributes(request);
            }
        }

        return page;
    }


    private void collectParams(HttpServletRequest request) {
        session = request.getSession();

        accountTypeParam = (String)session.getAttribute("account_type");
        type = AccountType.valueOf(accountTypeParam.toUpperCase());

        user = (User)session.getAttribute("user");

        String rateParam = request.getParameter("rate");
        if(rateParam.length() > 0)
            rate = Double.parseDouble(rateParam);

        if(type.equals(AccountType.DEPOSIT)) {
            creditLimit = 0.0;
            return;
        }

        String limitParam = request.getParameter("limit");
        if(limitParam.length() > 0) {
            creditLimit = Double.parseDouble(limitParam);
        }

    }

    private boolean checkParamsAndReport(HttpServletRequest request) {
        if(accountTypeParam == null && accountTypeParam == "" && user == null) {
            logger.log("Sorry? Can't create new account. Empty params.");
            request.setAttribute("message", "Sorry? Can't create new account. Empty params.");
            return false;
        } else
            return true;
    }

    private boolean checkAndReport(AccountType type, User user, HttpServletRequest request) {
        if(checkNewAccountRequestsAndReport(type, user, request)
                && checkActiveAccountsAndReport(user, type, request)
                && checkDepositIfCreditAccountCreating(creditLimit, user, type, request))
            return true;
        else
            return false;
    }

    // TODO:  Add check the request with same rate and limit
    private boolean checkNewAccountRequestsAndReport(AccountType type, User user, HttpServletRequest request) {
        System.out.println(type);
        if(type.equals(AccountType.DEPOSIT)) {
            return true;
        }

        NewAccountRequestDao dao = new NewAccountRequestDao();

        if(dao.isContainNotConfirmedByUserAndType(type, user)) {
            logger.log("Sorry? Can't create new account request. Request already in the list.");
            request.setAttribute("message", "Sorry? Can't create new account request. Request already in the list.");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkActiveAccountsAndReport(User user, AccountType type, HttpServletRequest request) {
        System.out.println(type);
        if(type.equals(AccountType.DEPOSIT)) {
            return true;
        }

        accountDao = new AccountDao();

        LinkedList<Account> allUserAccounts = accountDao.findByUserAndAccountType(user, type);

        if(allUserAccounts.size() > 0) {
            logger.log("Sorry? Can't create new credit account request. Credit account is active.");
            request.setAttribute("message", "Sorry? Can't create new account request. Credit account is active.");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkDepositIfCreditAccountCreating(double creditLimit, User user, AccountType type, HttpServletRequest request) {
        if(type.equals(AccountType.DEPOSIT)) {
            return true;
        }
        else if(creditLimit > getAllDepositsSum(user)) {
            return true;
        }
        else {
            logger.log("Sorry? Can't create new account request. You do not have minimal deposit balance.");
            request.setAttribute("message", "Sorry? Can't create new account request. You do not have minimal deposit balance.");
            return false;
        }
    }

    private double getAllDepositsSum(User user) {
        double sum = 0.0;
        LinkedList<Account> depositAccounts = accountDao.findByUserAndAccountType(user, AccountType.DEPOSIT);

        for(Account account : depositAccounts) {
            sum += account.getBalance();
        }

        return sum;
    }

    private void setAttributes(HttpServletRequest request) {

        request.setAttribute("message", "Request for the new account was added to list. Wait for admin response.");

    }
}

package roman.ironbank.controller.commands.commandImpl;


import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.account.*;
import roman.ironbank.domain.service.log.FileLogger;
import roman.ironbank.domain.service.log.Logger;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.impl.AccountDao;
import roman.ironbank.model.dao.impl.CreditFeaturesDao;
import roman.ironbank.model.dao.impl.NewAccountRequestDao;
import roman.ironbank.model.dao.impl.TransactionDao;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class CommandAcceptRequest implements Command {

    int newAccountRequestId;

    Logger logger = new FileLogger();

    NewAccountRequestDao newAccountRequestDao;
    NewAccountRequest newAccountRequest;
    AccountType type;
    AccountDao accountDao;
    User user;
    
    CreditFeaturesDao creditFeaturesDao;

    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.ADMIN_CABINET;

        collectParams(request);

        initVariables();

        if(checkPossibility()) {
            createAccount(newAccountRequestId);

            setRequestAsActive(newAccountRequest);

            setParams(request, createNewRequestList(), getAllActiveAccounts(), getAllTransactions());
        }

        return page;
    }

    private void collectParams(HttpServletRequest request) {
      String requestId = request.getParameter("request_id");
        if(requestId.length() != 0)
            newAccountRequestId = Integer.parseInt(requestId);
        else {
            logger.log("Error! Empty param field ('request_id')");
            request.setAttribute("message", "Error! Empty param field ('request_id')");
        }
    }

    private void initVariables() {
        try {
            newAccountRequestDao = new NewAccountRequestDao();
            newAccountRequest = newAccountRequestDao.findById(newAccountRequestId);
            accountDao = new AccountDao();
            user = newAccountRequest.getUser();
            type = newAccountRequest.getAccountType();
            creditFeaturesDao = new CreditFeaturesDao();
        } catch (NullPointerException n) {
            logger.log(n.getMessage());
        } catch (Exception e) {
            logger.log(e.getMessage());
        }

    }

    private boolean checkPossibility() {
        int accountsByType = accountDao.findByUserAndAccountType(user, type).size();

        if(type.equals(AccountType.CREDIT)
                & accountsByType > 0) {
            return false;
        } else
            return true;
    }

    private void createAccount(int newAccountRequestId) {
        try {
//            newAccountRequestDao = new NewAccountRequestDao(logger);
//            newAccountRequest = newAccountRequestDao.findById(newAccountRequestId);

            if(newAccountRequest != null) {
                double rate = newAccountRequest.getRate();
                double limit = newAccountRequest.getLimit();

                Account newAccount = new Account(type, user, 0.0, rate);// TODO Change constructor

                // TODO: Need to lock tables(account, credit_features)
                int accountId = accountDao.create(newAccount);
                if(accountId != 0) {
                    newAccount.setIdInDb(accountId);

                    CreditFeatures creditFeatures = new CreditFeatures(accountId, limit);
                    creditFeaturesDao.create(creditFeatures);
                }


            }
        } catch (Exception e) {
            // TODO: DaoException
        }
    }

    private void setRequestAsActive(NewAccountRequest accountRequest) {
//        accountRequest.setAccepted(true);
//        newAccountRequestDao = new NewAccountRequestDao(logger);
        newAccountRequestDao.setConfirmedById(newAccountRequestId);
    }

    private LinkedList<NewAccountRequest> createNewRequestList() {
       // NewAccountRequestDao  = new NewAccountRequestDao(new FileLogger());
        return newAccountRequestDao.findAllNotConfirmed();
    }

    private LinkedList<Account> getAllActiveAccounts() {
//        AccountDao accountDao = new AccountDao(logger);
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

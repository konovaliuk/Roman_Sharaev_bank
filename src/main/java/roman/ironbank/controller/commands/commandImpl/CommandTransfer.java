package roman.ironbank.controller.commands.commandImpl;


import org.apache.log4j.Logger;
import roman.ironbank.controller.commands.Command;
import roman.ironbank.controller.util.pages.Pages;
import roman.ironbank.domain.bank.Operator;

import javax.servlet.http.HttpServletRequest;

public class CommandTransfer implements Command {
    private static Logger logger = Logger.getLogger(CommandTransfer.class.getName());

    private int accountIdFrom;
    private int accountIdTo;
    private double sum;

    @Override
    public String execute(HttpServletRequest request) {
        String page = Pages.CREDIT_ACCOUNT;

        if(collectParams(request)) {
            if(operation()) {
                setParams(request);
//                page = Pages.CHECK;
            } else {
//                page = Pages.USER_CABINET;
                request.setAttribute("message", "Error! Transfer was not successful.");
            }
        }
        System.out.println("page: " + page);

        return page;
    }

    private boolean collectParams(HttpServletRequest request) {
        boolean result = false;

        String accountIdFromParam = request.getParameter("account_id");
        String accountIdToParam = request.getParameter("recipient");
        String sumParam = request.getParameter("sum");

        try{
            accountIdFrom = Integer.parseInt(accountIdFromParam);
            accountIdTo = Integer.parseInt(accountIdToParam);
            sum = Double.parseDouble(sumParam);

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
        boolean result = operator.pay(accountIdFrom, accountIdTo, sum);

        return result;
    }

    private void setParams(HttpServletRequest request) {
        request.setAttribute("message", "Transfer from account: " + accountIdFrom + " to account: " + accountIdTo + " was successful. Transfer amount: " + sum + ".");


    }
}

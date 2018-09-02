package roman.ironbank.domain.account;

import roman.ironbank.domain.users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DepositAccount extends Account implements InterfaceAccount {

    double interestIncome;  //percent income

    public DepositAccount() {
    }

    public DepositAccount(User user, double balance, LocalDate validityStart, LocalDate validityEnd, double rate) {
        super(AccountType.DEPOSIT, user, balance, validityStart, validityEnd, rate);
    }

    public DepositAccount(int idInDb, User user, double balance, LocalDate validityStart, LocalDate validityEnd, double rate) {
        super(idInDb, AccountType.DEPOSIT, user, balance, validityStart, validityEnd, rate);
    }

    @Override
    public void transfer(Double sum, String recipient) {

    }

    @Override
    public void info() {

    }
}

package roman.ironbank.domain.account;

import roman.ironbank.domain.users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditAccount extends Account implements InterfaceAccount {

    private CreditFeatures creditFeatures;
//    private double creditRate;
//    private LinkedList<PaymentHistory> paymentHistory;


    public CreditAccount() {
    }

    public CreditAccount(User user, double balance, LocalDate validityFrom, LocalDate validityTo, double rate, CreditFeatures creditFeatures) {
        super(AccountType.CREDIT, user, balance, validityFrom, validityTo, rate);
        this.creditFeatures = creditFeatures;
    }

    public CreditAccount(int idInDb, User user, double balance, LocalDate validityFrom, LocalDate validityTo, double rate, CreditFeatures creditFeatures) {
        super(idInDb,  AccountType.CREDIT, user, balance, validityFrom, validityTo, rate);
        this.creditFeatures = creditFeatures;
    }

    public CreditFeatures getCreditFeatures() {
        return creditFeatures;
    }

    public void setCreditFeatures(CreditFeatures creditFeatures) {
        this.creditFeatures = creditFeatures;
    }

    @Override
    public void transfer(Double sum, String recipient) {

    }

    @Override
    public void info() {

    }


}
package roman.ironbank.domain.account;

import roman.ironbank.domain.users.User;

import java.time.LocalDate;
import java.util.LinkedList;

public class Account {
    private Integer idInDb;

    private AccountType type;

    private User user;

    private double balance;

    private double rate;

    private LocalDate validityFrom;
    private LocalDate validityTo;

    private LinkedList<Transaction> hist;

    /**
     * Constructors
     */
    public Account() {
    }
    /**
     * Standart constructor
     * @param type
     * @param user
     */
    public Account(AccountType type, User user, double balance, double rate) {
        this.type = type;
        this.user = user;
        this.balance = balance;
        validityFrom = LocalDate.now();
        validityTo = LocalDate.now().plusYears(1);
        this.rate = rate;
    }
    /**
     * Initialize without idInDb and history
     * @param type  account type name
     * @param user  owner
     * @param balance   current balance
     * @param validityFrom  date and time of account opening
     * @param validityTo    end date and time
     * @param rate  credit or deposit rate
     */
    public Account(AccountType type, User user, double balance, LocalDate validityFrom, LocalDate validityTo, double rate) {
        this.type = type;
        this.user = user;
        this.balance = balance;
        this.rate = rate;
        this.validityFrom = validityFrom;
        this.validityTo = validityTo;
    }
    /**
     * Initialize all fields
     * @param idInDb    id in the datebase
     * @param type  account type name
     * @param user  owner
     * @param balance   current balance
     * @param validityFrom  date and time of account opening
     * @param validityTo    end date and time
     * @param rate  credit or deposit rate
     */
    public Account(Integer idInDb, AccountType type, User user, double balance, LocalDate validityFrom, LocalDate validityTo, double rate) {
        this.idInDb = idInDb;
        this.type = type;
        this.user = user;
        this.balance = balance;
        this.validityFrom = validityFrom;
        this.validityTo = validityTo;
        this.rate = rate;
    }
    /**
     * Getters
     */
    /**
     * Getter id
     * @return  id in the datebase
     */
    public Integer getIdInDb() {
        return idInDb;
    }

    /**
     * Getter type
     * @return  account type name
     */
    public AccountType getType() {
        return type;
    }

    /**
     * Getter user
     * @return  owner
     */
    public User getUser() {
        return user;
    }
    /**
     * Getter balance
     * @return  current balance
     */
    public double getBalance() {
        return balance;
    }
    /**
     * Getter rate
     * @return  credit or deposit rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Getter validityFrom
     * @return  date and time of account opening
     */
    public LocalDate getValidityFrom() {
        return validityFrom;
    }
    /**
     * Getter validityTo
     * @return  end date and time
     */
    public LocalDate getValidityTo() {
        return validityTo;
    }
    /**
     * Getter history
     * @return  list of all transactions
     */
    public LinkedList<Transaction> getHist() {
        return hist;
    }
    /**
     * Setters
     */

    /**
     * Setter id in the database
     * @param idInDb
     */
    public void setIdInDb(Integer idInDb) {
        this.idInDb = idInDb;
    }
    /**
     * Setter balance
     * @param balance   to change balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     * Setter rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Setter history
     * @param hist   for add new transactions
     */
    public void setHist(LinkedList<Transaction> hist) {
        this.hist = hist;
    }

    /**
     * Setter validityTo
     * @param validityTo    for prolongation
     */
    public void setValidityTo(LocalDate validityTo) {
        this.validityTo = validityTo;
    }

}

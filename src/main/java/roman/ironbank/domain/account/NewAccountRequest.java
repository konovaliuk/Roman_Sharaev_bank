package roman.ironbank.domain.account;

import roman.ironbank.domain.users.User;

import java.time.LocalDate;

public class NewAccountRequest {
    private Integer idInDb;
    private AccountType accountType;
    private User user;
    private LocalDate date;
    private boolean isAccepted;
    private boolean isDeclined;
    private double rate;
    private double limit;

    public NewAccountRequest() {
    }

    public NewAccountRequest(AccountType accountType, User user, double rate, double limit) {
        this.accountType = accountType;
        this.user = user;
        this.rate =rate;
        this.limit=limit;

    }

    public NewAccountRequest(Integer idInDb, AccountType accountType, User user, LocalDate date, boolean isAccepted, boolean isDeclined, double rate, double limit) {
        this.idInDb = idInDb;
        this.accountType = accountType;
        this.user = user;
        this.date = date;
        this.isAccepted = isAccepted;
        this.isDeclined = isDeclined;
        this.rate = rate;
        this.limit = limit;
    }

    /**
     * Getters
     * @return
     */
    public Integer getIdInDb() {
        return idInDb;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isDeclined() {
        return isDeclined;
    }

    public double getRate() {
        return rate;
    }

    public double getLimit() {
        return limit;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void setDeclined(boolean declined) {
        isDeclined = declined;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
    /**
     * Setters
     * @param idInDb
     */

    public void setIdInDb(Integer idInDb) {

        this.idInDb = idInDb;
    }
}

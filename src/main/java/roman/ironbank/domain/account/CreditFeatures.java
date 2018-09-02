package roman.ironbank.domain.account;

public class CreditFeatures {
    private Integer accountId;
    private double creditLimit;
    private double arrears;
    /**
     * Constructors
     */
    public CreditFeatures() {
    }

    public CreditFeatures(Integer accountId, double creditLimit) {
        this.accountId = accountId;
        this.creditLimit = creditLimit;
    }

    public CreditFeatures(Integer accountId, double creditLimit, double arrears) {
        this.accountId = accountId;
        this.creditLimit = creditLimit;
        this.arrears = arrears;
    }
    /**
     * Getters
     */
    public Integer getAccountId() {
        return accountId;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getArrears() {
        return arrears;
    }
    /**
     * Setters
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }
}

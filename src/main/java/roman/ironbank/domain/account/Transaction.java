package roman.ironbank.domain.account;


import java.time.LocalDateTime;

public class Transaction {
    private int idInDb;
    private LocalDateTime time_stamp;
    private double amount;
    private Account sender;
    private Account recipient;


    public Transaction() {
    }

    public Transaction(LocalDateTime time_stamp, double amount, Account sender, Account recipient) {
        this.time_stamp = time_stamp;
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Transaction(int idInDb, LocalDateTime time_stamp, double amount, Account sender, Account recipient) {
        this.idInDb = idInDb;
        this.time_stamp = time_stamp;
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }


    public void setIdInDb(int idInDb) {
        this.idInDb = idInDb;
    }

    public int getIdInDb() {
        return idInDb;
    }

    public LocalDateTime getTime_stamp() {
        return time_stamp;
    }

    public double getAmount() {
        return amount;
    }

    public Account getSender() {
        return sender;
    }

    public Account getRecipient() {
        return recipient;
    }
}

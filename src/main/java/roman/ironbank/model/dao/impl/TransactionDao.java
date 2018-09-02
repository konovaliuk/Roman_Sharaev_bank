package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.Transaction;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.ITransactionDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class TransactionDao implements ITransactionDao {
    private static final Logger logger = Logger.getLogger(TransactionDao.class.getName());

    private static final String SQL_CREATE_TRANSACTION = "INSERT INTO mydb.transaction (amount, sender_account_id, recipient_account_id) VALUES(?,?,?);";
    private static final String SQL_FIND_ALL_TRANSACTION = "SELECT * FROM mydb.transaction;";
    private static final String SQL_FIND_TRANSACTION_BY_ID = "SELECT * FROM mydb.transaction WHERE id = ?;";
    private static final String SQL_FIND_TRANSACTION_BY_ACCOUNT_ID = "SELECT * FROM mydb.transaction WHERE sender_account_id = ? OR recipient_account_id = ?;";

    @Override
    public int create(Transaction entity) {
        int generatedId = 0;

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_TRANSACTION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1,entity.getAmount());

            int senderId = entity.getSender().getIdInDb();
            statement.setInt(2, senderId);

            int recipientId = entity.getRecipient().getIdInDb();
            statement.setInt(3,recipientId);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()){
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.trace("can't create new transaction");
        }
        logger.trace("Transaction created with id: " + generatedId);
        return generatedId;
    }

    @Override
    public LinkedList<Transaction> findAll() {
        LinkedList<Transaction> transactions = new LinkedList<>();
        ResultSet rs = null;

        try (Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(SQL_FIND_ALL_TRANSACTION);

            while(rs.next()){
                int id = rs.getInt(1);
                LocalDateTime dateTime = rs.getTimestamp(2).toLocalDateTime();
                double amount = rs.getByte(3);

                int senderId = rs.getInt(4);
                AccountDao accountDao = new AccountDao();
                Account senderAccount = accountDao.findById(senderId);
                int recipientId = rs.getInt(5);
                Account recipientAccount = accountDao.findById(recipientId);

                Transaction transaction = new Transaction(id,dateTime, amount,senderAccount,recipientAccount);
                transactions.add(transaction);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.trace("Fount " + transactions.size() + " transactions");
        return transactions;
    }

    @Override
    public Transaction findById(int id) {
        Transaction transaction = null;
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_TRANSACTION_BY_ID)) {
            statement.setInt(1,id);
            rs = statement.executeQuery();

            while(rs.next()){
                id = rs.getInt(1);

                LocalDateTime time = rs.getTimestamp(2).toLocalDateTime();

                double amount = rs.getDouble(3);

                int senderId = rs.getInt(4);
                AccountDao accountDao = new AccountDao();
                Account senderAccount = accountDao.findById(senderId);

                int recipientId = rs.getInt(5);
                Account recipientAccount = accountDao.findById(recipientId);

                transaction = new Transaction(id, time, amount, senderAccount, recipientAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.trace("Found by id: " + id);
        return transaction;
    }

    @Override
    public LinkedList<Transaction> findByAccountId(int accountId) {
        LinkedList<Transaction> transactions = new LinkedList<>();
        ResultSet rs = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_TRANSACTION_BY_ACCOUNT_ID)) {

            statement.setInt(1, accountId);
            statement.setInt(2, accountId);
            rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);

                LocalDateTime time_stamp = rs.getTimestamp(2).toLocalDateTime();
                double amount = rs.getDouble(3);

                int senderAccountId = rs.getInt(4);
                AccountDao accountDao = new AccountDao();
                Account senderAccount = accountDao.findById(senderAccountId);

                int recipientAccountId = rs.getInt(5);
                Account recipientAccount = accountDao.findById(recipientAccountId);

                Transaction transaction = new Transaction(id, time_stamp, amount, senderAccount, recipientAccount);

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.trace("Found " + transactions.size() + " by account: " + accountId);
        return transactions;
    }

}

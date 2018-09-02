package roman.ironbank.domain.bank;

import org.apache.log4j.Logger;
import roman.ironbank.model.dao.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Operator {
    private static Logger logger = Logger.getLogger(Operator.class.getName());
    private static volatile Operator instance;

    private Operator() {}

    public static Operator getInstance() {
        if(instance == null) {
            synchronized (Operator.class) {
                if(instance == null)
                    instance = new Operator();
            }
        }
        return instance;
    }

    private static final String SQL_REPLENISH_QUERY = "UPDATE mydb.account SET balance = balance + ? WHERE id = ?;";
    private static final String SQL_CREATE_TRANSACTION_NOTATION = "INSERT INTO mydb.transaction(amount, sender_account_id, recipient_account_id) VALUES(?, ?, ?);";

    public boolean pay(int accountId, double sum) throws SQLException {
        boolean result = false;

        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getConnection();
            statement = connection.prepareStatement(SQL_REPLENISH_QUERY);

            connection.setAutoCommit(false);

//            int isolation = connection.getTransactionIsolation();
//            System.out.println("transaction isolation: " + isolation);

            statement.setDouble(1, sum);
            statement.setInt(2, accountId);

            int res = statement.executeUpdate();
            System.out.println("res: " + res);
//            result = statement.execute();

            if(res == 1) {
                connection.commit();
                logger.trace("Replenish account " + accountId + " was successful");
            } else {
                connection.rollback();
                logger.trace("Replenish " + accountId + " was not successful");
            }

            if(res == 1) {
                statement = connection.prepareStatement(SQL_CREATE_TRANSACTION_NOTATION);
                statement.setDouble(1, sum);
                statement.setInt(2, accountId);
                statement.setInt(3, accountId);

                result = statement.execute();

                if(res == 1) {
                    connection.commit();
                    result = true;
                    logger.trace("Transaction was marked successful");
                } else {
                    connection.rollback();
                    logger.trace("Replenish was not marked");
                }
            }

            connection.setAutoCommit(true);

        } catch (SQLException e) {
            logger.error(e.getMessage());
            try{
                connection.rollback();
            } catch (SQLException sqlEx) {
                logger.error(sqlEx.getMessage());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            try{
                connection.rollback();
            } catch (SQLException sqlExc) {
                logger.error(sqlExc.getMessage());
            }
        } finally {
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }

        return result;
    }

    private static final String SQL_GET_BALANCE = "SELECT balance FROM mydb.account WHERE id = ?;";
    private static final String SQL_GET_LIMIT = "SELECT credit_limit FROM mydb.credit_features WHERE mydb.account.id = ?;";
    private static final String SQL_MINUS_BALANCE = "UPDATE mydb.account SET balance = balance - ? WHERE id = ?;";
    private static final String SQL_PLUS_BALANCE = "UPDATE mydb.account SET balance = balance + ? WHERE id = ?;";

    public boolean pay(int sender, int recipient, double sum) {
        boolean result = false;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        double balance = 0.0;
        double limit = 0.0;

        try(Connection connection = ConnectionPool.getConnection()) {

            statement = connection.prepareStatement(SQL_GET_BALANCE);

            statement.setInt(1, sender);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                balance = resultSet.getDouble(1);
            }

            statement = connection.prepareStatement(SQL_GET_LIMIT);

            statement.setInt(1, sender);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                limit = resultSet.getDouble(1);
            }

            if((balance - sum) < (limit - limit - limit)) {
                logger.error("You can't transfer this sum. You will exceed the credit limit.");
                connection.rollback();
                return result;
            } else {
                connection.setAutoCommit(false);

                statement = connection.prepareStatement(SQL_MINUS_BALANCE);
                statement.setDouble(1, sum);
                statement.setInt(2, sender);
                int res = statement.executeUpdate();

                if(res == 1) {
                    res = 0;
                    statement = connection.prepareStatement(SQL_PLUS_BALANCE);
                    statement.setDouble(1, sum);
                    statement.setInt(2, recipient);
                    res = statement.executeUpdate();

                    if(res ==1) {
                        res = 0;
                        statement = connection.prepareStatement(SQL_CREATE_TRANSACTION_NOTATION);
                        statement.setDouble(1, sum);
                        statement.setInt(2, sender);
                        statement.setInt(3, recipient);
                        res = statement.executeUpdate();
                        if(res == 1) {
                            result = true;
                            connection.commit();
                            logger.trace("Transfer from account: " + sender + " to account: " + recipient + " was successful. Transfer amount: " + sum + ".");
                        } else {
                            logger.error("Can't create transaction notation.");
                            connection.rollback();
                        }
                    } else {
                        logger.error("Can't change balance in recipient account");
                        connection.rollback();
                    }
                } else {
                    logger.error("Can't change balance in sender account");
                    connection.rollback();
                }
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            // TODO:  Need rollback() here.
        }

        return result;
    }
}

package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.account.Account;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.IAccountDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class AccountDao implements IAccountDao {

    private static final Logger logger = Logger.getLogger(AdminDao.class.getName());
    private static final String SQL_CREATE_ACCOUNT = "INSERT INTO mydb.account (id, balance, rate, validity_from, validity_to, user_id) VALUES(?,?,?,?,?,?);";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM mydb.account;";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM mydb.account WHERE id = ?;";
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE mydb.account SET type_id = ?, user_id = ?, balance = ?, rate = ?, validity_to = ? WHERE id = ?;";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM mydb.account WHERE id = ?;";
    private static final String SQL_FIND_ACCOUNTS_BY_USER_ID = "SELECT * FROM mydb.account WHERE user_id = ?;";
    private static final String SQL_FIND_ACCOUNT_BY_USER_AND_TYPE_ID = "SELECT * FROM mydb.account WHERE user_id = ? AND type_id = ?;";

    @Override
    public int create(Account entity) {
        int generatedId = 0;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ACCOUNT)) {

            AccountTypeDao accountTypeDao = new AccountTypeDao();
            int typeId = accountTypeDao.findId(entity.getType());
            statement.setInt(1,typeId);

            int userId = entity.getUser().getIdInDb();

            statement.setInt(2,userId);

            statement.setDouble(3,entity.getBalance());

            statement.setDouble(4,entity.getRate());

            Date validityStart = Date.valueOf(entity.getValidityFrom());
            statement.setDate(5,validityStart);

            Date validityEnd = Date.valueOf(entity.getValidityTo());
            statement.setDate(6,validityEnd);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()){
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.trace(e.getMessage());
        }

        return generatedId;
    }

    @Override
    public LinkedList<Account> findAll() {
        LinkedList<Account> accounts = new LinkedList<>();
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement()) {
        rs = statement.executeQuery(SQL_FIND_ALL_ACCOUNTS);

        while(rs.next()){
            int databaseId = rs.getInt("id");

            int typeId = rs.getInt("type_id");

            AccountTypeDao accountTypeDao = new AccountTypeDao();
            AccountType type = accountTypeDao.findById(typeId);

            int userId = rs.getInt("user_id");
            UserDao userDao = new UserDao();
            User user = userDao.findById(userId);

            double balance = rs.getDouble("balance");
            double rate = rs.getDouble("rate");

            LocalDate validityStart = rs.getDate("validity_Start").toLocalDate();
            LocalDate validityEnd = rs.getDate("validity_end").toLocalDate();

            Account account = new Account(databaseId,type,user,balance,validityStart,validityEnd,rate);

            accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.trace(e.getMessage());
            }
        }
        return accounts;
    }

    @Override
    public Account findById(int id) {
        Account account = null;
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID)) {
            statement.setInt(1,id);
            rs = statement.executeQuery();

            while(rs.next()){
                int typeId = rs.getInt("type_id");
                AccountTypeDao accountTypeDao = new AccountTypeDao();
                AccountType type = accountTypeDao.findById(typeId);

                int userId = rs.getInt("user_id");
                UserDao userDao = new UserDao();
                User user = userDao.findById(userId);

                double balance = rs.getDouble("balance");

                double rate = rs.getDouble("rate");

                LocalDate validityStart = rs.getDate("validity_start").toLocalDate();

                LocalDate validityEnd = rs.getDate("validity_end").toLocalDate();

                account = new Account(id, type, user, balance, validityStart, validityEnd, rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.trace(e.getMessage());
            }
        }
        return account;
    }

    @Override
    public LinkedList<Account> findByUserEmail(String email) {
        LinkedList<Account> accounts = new LinkedList<>();
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_USER_ID)) {

            UserDao userDao = new UserDao();
            User user = userDao.findByEmail(email);

            statement.setInt(1, user.getIdInDb());
            rs = statement.executeQuery();

            while (rs.next()) {
                int idInDb = rs.getInt("id");

                int typeId = rs.getInt("type_id");
                AccountTypeDao accountTypeDao = new AccountTypeDao();
                AccountType type = accountTypeDao.findById(typeId);

                double balance = rs.getDouble("balance");

                double rate = rs.getDouble("rate");

                LocalDate validityFrom = rs.getDate("validity_start").toLocalDate();

                LocalDate validityTo = rs.getDate("validity_end").toLocalDate();


                Account account = new Account(idInDb, type, user, balance, validityFrom, validityTo, rate);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.trace(e.getMessage());
            }
        }
        return accounts;
    }

    @Override
    public LinkedList<Account> findByUserAndAccountType(User user, AccountType type) {
        LinkedList<Account> accounts = new LinkedList<>();
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_USER_AND_TYPE_ID)) {

            int userId = user.getIdInDb();
            if(userId == 0) {
                UserDao userDao = new UserDao();
                user = userDao.findByEmail(user.getEmail());
                userId = user.getIdInDb();
            }
            statement.setInt(1, user.getIdInDb());

            AccountTypeDao accountTypeDao = new AccountTypeDao();
            int typeId = accountTypeDao.findId(type);
            statement.setInt(2, typeId);

            rs = statement.executeQuery();

            while (rs.next()) {
                int idInDb = rs.getInt("id");

                double balance = rs.getDouble("balance");

                double rate = rs.getDouble("rate");

                LocalDate validityStart = rs.getDate("validity_from").toLocalDate();

                LocalDate validityEnd = rs.getDate("validity_to").toLocalDate();


                Account account = new Account(idInDb, type, user, balance, validityStart, validityEnd, rate);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.trace(e.getMessage());
            }
        }
        return accounts;
    }


    @Override
    public boolean update(Account entity) {
        boolean result = false;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT)) {
        AccountTypeDao accountTypeDao = new AccountTypeDao();
        int typeId = accountTypeDao.findId(entity.getType());
        statement.setInt(1, typeId);

        int userId = entity.getUser().getIdInDb();
        statement.setInt(2,userId);

        statement.setDouble(3, entity.getBalance());

        statement.setDouble(4,entity.getRate());

        Date validityEnd = Date.valueOf(entity.getValidityTo());
        statement.setDate(5,validityEnd);
        statement.setInt(6,entity.getIdInDb());

        result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Account entity) {
        boolean result = false;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT)) {
        statement.setInt(1,entity.getIdInDb());

        result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

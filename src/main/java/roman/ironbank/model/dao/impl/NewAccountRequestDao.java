package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.domain.account.NewAccountRequest;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.INewAccountRequestDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;


public class NewAccountRequestDao implements INewAccountRequestDao{
    private static final Logger logger = Logger.getLogger(NewAccountRequestDao.class.getName());
    private static final String SQL_CREATE_NEW_ACCOUNT_REQUEST = "INSERT INTO mydb.new_account_request(type_id, user_id, date, is_accepted, is_declined, rate, credit_limit) VALUES(?,?,?,?,?,?,?);";
    private static final String SQL_FIND_ALL_NEW_ACCOUNT_REQUEST = "SELECT * FROM mydb.new_account_request;";
    private static final String SQL_FIND_NEW_ACCOUNT_BY_ID = "SELECT * FROM mydb.new_account_request WHERE id = ?;";
    private static final String SQL_FIND_ALL_NEW_ACCOUNT_REQUESTS_NOT_CONFIRMED = "SELECT * FROM iron_bank.new_account WHERE is_approved = false AND is_declined = false;";
    private static final String SQL_SET_APPROVED_BY_ID = "UPDATE mydb.new_account_request SET is_accepted = true WHERE id = ?;";
    private static final String SQL_SET_DECLINED_BY_ID = "UPDATE mydb.new_account_request SET is_declined = true WHERE id = ?;";
    private static final String SQL_FIND_ALL_DECLINED = "SELECT * FROM mydb.new_account_request WHERE is_accepted = false AND is_declined = true;";
    private static final String SQL_FIND_ACCOUNT_REQUEST_BY_TYPE_ID_AND_USER_ID_NOT_CONFIRMED = "SELECT id FROM mydb.new_account_request WHERE type_id = ? AND user_id = ? AND  is_accepted = false AND is_declined = false;";;


    @Override
    public int create(NewAccountRequest entity) {
        int generatedId = 0;
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_NEW_ACCOUNT_REQUEST)) {
                AccountTypeDao accountTypeDao = new AccountTypeDao();
            AccountType accountType = entity.getAccountType();
            int typeId = accountTypeDao.findId(accountType);
            int userId = entity.getUser().getIdInDb();
            if(userId == 0){
                UserDao userDao = new UserDao();
                String email = entity.getUser().getEmail();
                User user = userDao.findByEmail(email);
                userId = user.getIdInDb();
            }

            statement.setInt(1, typeId);
            statement.setInt(2,userId);;
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.setDouble(4,entity.getRate());
            statement.setDouble(5,entity.getLimit());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();

            while(rs.next()){
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public LinkedList<NewAccountRequest> findAll() {
        LinkedList<NewAccountRequest> requests = new LinkedList<>();
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(SQL_FIND_ALL_NEW_ACCOUNT_REQUEST);
            while(rs.next()){
                int id = rs.getInt(1);
                int typeId = rs.getInt(2);
                int userId = rs.getInt(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                boolean isApproved = rs.getBoolean(5);
                boolean isDeclined = rs.getBoolean(6);
                double rate = rs.getDouble(7);
                double limit = rs.getDouble(8);

                AccountTypeDao accountTypeDao = new AccountTypeDao();
                AccountType accountType = accountTypeDao.findById(typeId);

                UserDao userDao = new UserDao();
                User user = userDao.findById(userId);

                NewAccountRequest newAccountRequest = new NewAccountRequest(id,accountType,user,date,isApproved,isDeclined,rate,limit);
                requests.add(newAccountRequest);
            }
            logger.trace("fount " + requests.size()+ " requests");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public NewAccountRequest findById(int id) {
        NewAccountRequest newAccountRequest = null;
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_NEW_ACCOUNT_BY_ID)) {
            statement.setInt(1,id);
            rs = statement.executeQuery();

            while(rs.next()){
                int typeId = rs.getInt(2);
                int userId = rs.getInt(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                boolean isApproved = rs.getBoolean(5);
                boolean isDeclined = rs.getBoolean(6);
                double rate = rs.getDouble(7);
                double limit = rs.getDouble(8);


                AccountTypeDao accountTypeDao = new AccountTypeDao();
                AccountType accountType = accountTypeDao.findById(typeId);

                UserDao userDao = new UserDao();
                User user = userDao.findById(userId);

                newAccountRequest = new NewAccountRequest(id, accountType, user, date, isApproved, isDeclined, rate, limit);

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
        return newAccountRequest;
    }

    @Override
    public LinkedList<NewAccountRequest> findAllNotConfirmed() {
        LinkedList<NewAccountRequest> requests = new LinkedList<>();
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(SQL_FIND_ALL_NEW_ACCOUNT_REQUESTS_NOT_CONFIRMED);

            while (rs.next()) {
                int id = rs.getInt(1);
                int typeId = rs.getInt(2);
                int userId = rs.getInt(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                boolean isAccepted = rs.getBoolean(5);
                boolean isDeclined = rs.getBoolean(6);
                double rate = rs.getDouble(7);
                double limit = rs.getDouble(8);

                AccountTypeDao accountTypeDao = new AccountTypeDao();
                AccountType accountType = accountTypeDao.findById(typeId);

                UserDao userDao = new UserDao();
                User user = userDao.findById(userId);

                NewAccountRequest newAccountRequest = new NewAccountRequest(id, accountType, user, date, isAccepted, isDeclined, rate, limit);
                requests.add(newAccountRequest);
            }

            logger.trace("found " + requests.size() + " requests.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public boolean setConfirmedById(int id) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SET_APPROVED_BY_ID)) {

            statement.setInt(1, id);

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean setDeclinedById(int id) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SET_DECLINED_BY_ID)) {

            statement.setInt(1, id);

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isContainNotConfirmedByUserAndType(AccountType type, User user) {
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_REQUEST_BY_TYPE_ID_AND_USER_ID_NOT_CONFIRMED)) {

            AccountTypeDao accountTypeDao = new AccountTypeDao();
            int typeId = accountTypeDao.findId(type);

            int userId = user.getIdInDb();
            if(userId == 0) {
                UserDao userDao = new UserDao();
                String email = user.getEmail();
                User userDb = userDao.findByEmail(email);
                userId = userDb.getIdInDb();
            }

            statement.setInt(1, typeId);
            statement.setInt(2, userId);

            resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //  if can't check - can't add!
        return true;
    }

    public LinkedList<NewAccountRequest> findAllDeclined() {
        LinkedList<NewAccountRequest> requests = new LinkedList<>();
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(SQL_FIND_ALL_DECLINED);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int typeId = resultSet.getInt(2);
                int userId = resultSet.getInt(3);
                LocalDate date = resultSet.getDate(4).toLocalDate();
                boolean isAccepted = resultSet.getBoolean(5);
                boolean isDeclined = resultSet.getBoolean(6);
                double rate = resultSet.getDouble(7);
                double limit = resultSet.getDouble(8);

                AccountTypeDao accountTypeDao = new AccountTypeDao();
                AccountType accountType = accountTypeDao.findById(typeId);

                UserDao userDao = new UserDao();
                User user = userDao.findById(userId);

                NewAccountRequest newAccountRequest = new NewAccountRequest(id, accountType, user, date, isAccepted, isDeclined, rate, limit);
                requests.add(newAccountRequest);
            }

            logger.trace("found " + requests.size() + " requests.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }
}

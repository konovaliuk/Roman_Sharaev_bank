package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.account.AccountType;
import roman.ironbank.model.dao.GenericDao;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.IAccountDao;
import roman.ironbank.model.dao.interfaces.IAccountTypeDao;

import java.sql.*;
import java.util.LinkedList;

public class AccountTypeDao implements IAccountTypeDao {

    private static final Logger logger = Logger.getLogger(AccountTypeDao.class.getName());

    private static final String SQL_CREATE_ACCOUNT_TYPE = "INSERT INTO mydb.account_type (name) VALUES(?);";
    private static final String SQL_FIND_ALL_ACCOUNT_TYPES = "SELECT * FROM mydb.account_type WHERE id = ?;";
    private static final String SQL_FIND_ACCOUNT_TYPE_BY_ID = "SELECT name FROM mydb.account_type WHERE id = ?;";
    private static final String SQL_FIND_ACCOUNT_BY_NAME = "SELECT name FROM mydb.account_type WHERE name = ?;";
    private static final String SQL_UPDATE_ACCOUNT_TYPE = "UPDATE mydb.account_type SET name = ? WHERE name = ?;";
    private static final String SQL_DELETE_ACCOUNT_TYPE = "DELETE FROM mydb.account_type WHERE name = ?;";



    @Override
    public int create(AccountType entity) {
        int newGeneratedId = 0;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ACCOUNT_TYPE, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,entity.name());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()){
                newGeneratedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newGeneratedId;
    }

    @Override
    public LinkedList<AccountType> findAll(){
    LinkedList<AccountType> types = new LinkedList<>();
    ResultSet rs = null;
    try(Connection connection = ConnectionPool.getConnection();
    Statement statement = connection.createStatement()){
        rs = statement.executeQuery(SQL_FIND_ALL_ACCOUNT_TYPES);

        while(rs.next()){
            String name = rs.getString("name");
            AccountType type = AccountType.valueOf(name);
            types.add(type);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return types;
    }

    @Override
    public AccountType findById(int id) {
        AccountType type = null;
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_TYPE_BY_ID)){
            statement.setInt(1,id);
            rs = statement.executeQuery();

            while(rs.next()){
                type = AccountType.valueOf(rs.getString(1).toUpperCase());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

//    public AccountType findByName(String name) {
//        AccountType type = null;
//        ResultSet rs = null;
//        try(Connection connection = ConnectionPool.getConnection();
//        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_NAME)){
//
//            statement.setString(1,name);
//            statement.executeQuery();
//            while(rs.next()){
//                type = AccountType.valueOf(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return type;
//    }

    public int findId(AccountType entity){
        int id = 0;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_NAME)) {

            statement.setString(1, entity.name());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public boolean update(AccountType entity) {
        boolean result = false;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_TYPE)){
            statement.setString(1,entity.name());
            int id = findId(entity);

            if(id == 0) return result;

            statement.setInt(2,id);
            result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean delete(AccountType entity) {
        boolean result = false;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_TYPE)){
            statement.setString(1,entity.name());
            result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

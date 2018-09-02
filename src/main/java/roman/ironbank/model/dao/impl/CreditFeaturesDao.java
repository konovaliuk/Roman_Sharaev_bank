package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.account.CreditFeatures;
import roman.ironbank.model.dao.GenericDao;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.ICreditFeaturesDao;

import java.sql.*;
import java.util.LinkedList;

public class CreditFeaturesDao implements ICreditFeaturesDao {

    private static final Logger logger = Logger.getLogger(CreditFeaturesDao.class.getName());
    private static final String SQL_CREATE_CREDIT_FEATURES = "INSERT INTO mydb.credit_features (credit_limit, indebtedness, account_id) VALUES(?, ?, ?);";
    private static final String SQL_FIND_ALL_CREDIT_FEATURES = "SELECT * FROM mydb.credit_features;";
    private static final String SQL_FIND_CREDIT_FEATURES_BY_ID = "SELECT * FROM mydb.credit_features WHERE account_id = ?;";
    private static final String SQL_UPDATE_CREDIT_FEATURES = "UPDATE mydb.credit_features SET account_id = ?, credit_limit = ?, indebtedness = ? WHERE account_id = ?;";
    private static final String SQL_DELETE_CREDIT_FEATURES = "DELETE FROM mydb.credit_features WHERE account_id = ?;";


    @Override
    public int create(CreditFeatures entity) {
        int id = 0;
        try(Connection connection  = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CREDIT_FEATURES)){

            statement.setInt(1, entity.getAccountId());
            statement.setDouble(2, entity.getCreditLimit());
            statement.setDouble(3,entity.getArrears());
            statement.execute();

            id = entity.getAccountId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public LinkedList<CreditFeatures> findAll() {
        LinkedList<CreditFeatures> features = new LinkedList<>();
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement()){
            rs = statement.executeQuery(SQL_FIND_ALL_CREDIT_FEATURES);

            while(rs.next()){
                int accountId = rs.getInt(1);
                double creditLimit = rs.getDouble(2);
                double arrears = rs.getDouble(3);

                CreditFeatures cf = new CreditFeatures(accountId,creditLimit,arrears);
                features.add(cf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return features;
    }

    @Override
    public CreditFeatures findById(int id) {
        CreditFeatures creditFeatures = null;
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_CREDIT_FEATURES_BY_ID)){
            while(rs.next()){
                int accountId = rs.getInt(1);
                double limit = rs.getDouble(2);
                double arrears = rs.getDouble(3);

                creditFeatures = new CreditFeatures(accountId,limit,arrears);
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
        return creditFeatures;
    }

//    @Override
//    public CreditFeatures findByName(String name) {
//
//        return null;
//    }


    public boolean update(CreditFeatures entity) {
       boolean res = false;
       try(Connection connection = ConnectionPool.getConnection();
       PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CREDIT_FEATURES)){
           statement.setInt(1,entity.getAccountId());
           statement.setDouble(2,entity.getCreditLimit());
           statement.setDouble(3,entity.getArrears());
           res = statement.execute();

       } catch (SQLException e) {
           e.printStackTrace();
       }
        return res;
    }


    public boolean delete(CreditFeatures entity) {
        boolean res = false;

        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CREDIT_FEATURES)) {
            statement.setInt(1,entity.getAccountId());
            res = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
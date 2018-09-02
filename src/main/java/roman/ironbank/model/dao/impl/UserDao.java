package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.users.User;
import roman.ironbank.model.dao.GenericDao;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.IUserDao;

import java.sql.*;
import java.util.LinkedList;

public class UserDao implements IUserDao {

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    private static final String SQL_CREATE_USER = "INSERT INTO mydb.user(name, email, pass) VALUES(?, ?, ?);";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM mydb.user;";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM mydb.user WHERE user_id = ?;";
    private static final String SQL_FIND_USER_BY_NAME = "SELECT * FROM mydb.user WHERE name = ?;";
    private static final String SQL_UPDATE_USER = "UPDATE mydb.user SET name = ?, pass= ? WHERE email = ?;";
    private static final String SQL_DELETE_USER = "DELETE FROM mydb.user WHERE email = ?;";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM mydb.user WHERE email = ?;";

    @Override
    public int create(User entity) {
        int newGeneratedID = 0;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS)){

                statement.setString(1, entity.getName());
                statement.setString(2, entity.getEmail());
                statement.setString(3, entity.getPass());

                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                while (rs.next()){
                    newGeneratedID = rs.getInt(1);
                    logger.trace("user: " + entity.getName()+ " has been registered");
                }
            } catch (SQLException e1) {
            e1.printStackTrace();
        }
        logger.trace("Registered with id: " + newGeneratedID);
        return newGeneratedID;
    }

    @Override
    public LinkedList<User> findAll() {
        LinkedList<User> users = new LinkedList<>();
        ResultSet rs = null;
        try(Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement()){
            rs = statement.executeQuery(SQL_FIND_ALL_USERS);
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String pass = rs.getString(4);

                User user = new User(id,name,email,pass);
                users.add(user);
            }
            logger.trace("found " + users.size() + " users");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = null;
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)){

            statement.setInt(1,id);
            rs = statement.executeQuery();

            while (rs.next()){
                String name = rs.getString(2);
                String email = rs.getString(3);
                String pass = rs.getString(4);

                user = new User(id, name, email, pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.trace("Found " +  " by id: " + id);
        return user;
    }

//    public User findByName(String name) {
//        User user = null;
//        ResultSet rs = null;
//
//        try(Connection connection = ConnectionPool.getConnection();
//        PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_NAME)){
//            statement.setString(1,name);
//            rs = statement.executeQuery();
//            while(rs.next()){
//                int id = rs.getInt(1);
//                String email = rs.getString(3);
//                String pass = rs.getString(4);
//
//                user = new User(id,name,email,pass);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        logger.trace("Found " + user.getEmail() + " by name" + name);
//        return user;
//    }

    public User findByEmail(String email){
        User user = null;
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement =connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)){
            statement.setString(1, email);
            rs= statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String pass = rs.getString(4);

                user = new User(id,name,email,pass);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.trace("Found by email " + email);
        return user;
    }
    @Override
    public boolean update(User entity) {
        boolean result = false;

        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)){
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getPass());
            statement.setString(3,entity.getPass());

            result = statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(User entity) {
        boolean result = false;

        try(Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)){
            statement.setString(1, entity.getName());
            result = statement.execute();

            if(result){
                logger.trace("user " + entity.getName() + "has been deleted");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}

package roman.ironbank.model.dao.impl;

import org.apache.log4j.Logger;
import roman.ironbank.domain.users.Admin;
import roman.ironbank.model.dao.GenericDao;
import roman.ironbank.model.dao.connection.ConnectionPool;
import roman.ironbank.model.dao.interfaces.IAdminDao;

import java.sql.*;
import java.util.LinkedList;

public class AdminDao implements IAdminDao{
    private static final Logger logger = Logger.getLogger(AdminDao.class.getName());
    private static final String SQL_CREATE_ADMIN ="INSERT INTO mydb.admin(email, pass) VALUES(?,?);";
    private static final String SQL_FIND_ALL_ADMINS = "SELECT * FROM mydb.admin;";
//    private static final String SQL_FIND_ADMIN_BY_ID ="SELECT * FROM mydb.admin where id = ?;";
    private static final String SQL_UPDATE_ADMIN = "UPDATE mydb.admin SET pass = ? WHERE email = ?;";
//    private static final String SQL_DELETE_ADMIN = "DELETE FROM mydb.admin WHERE email = ?;";
    private static final String SQL_FIND_ADMIN_BY_EMAIL = "SELECT * FROM mydb.admin WHERE email = ?;";

    @Override
    public int create(Admin entity) {
        int newGeneratedId = 0;
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ADMIN)){
            statement.setString(1,entity.getEmail());
            statement.setString(2,entity.getPass());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            while(rs.next()){
                newGeneratedId = rs.getInt(1);
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
        logger.trace("admin has been registered and have id: ");
        return newGeneratedId;
    }

    @Override
    public LinkedList<Admin> findAll() {
        LinkedList<Admin> admins = new LinkedList<>();
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(SQL_FIND_ALL_ADMINS);

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String pass = resultSet.getString(3);

                Admin admin = new Admin(id, email, pass);
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            logger.trace("Number of all admins: " + admins.size());
        return admins;
    }


//    public Admin findById(int id) {
//        Admin admin = null;
//        ResultSet rs = null;
//        try(Connection connection = ConnectionPool.getConnection();
//      final  PreparedStatement statement = connection.prepareStatement(SQL_FIND_ADMIN_BY_ID)){
//            statement.setInt(1,id);
//            rs = statement.executeQuery();
//            while (rs.next()){
//                String email = rs.getString(2);
//                String pass = rs.getString(3);
//
//                admin = new Admin(id, email, pass);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        logger.trace("find by " + "id" + admin.getAdmin_id());
//        return null;
//    }

    @Override
    public Admin findByEmail(String email) {
        Admin admin = null;
        ResultSet rs = null;

        try(Connection connection = ConnectionPool.getConnection();
           PreparedStatement statement = connection.prepareStatement(SQL_FIND_ADMIN_BY_EMAIL)) {

            statement.setString(1, email);
            rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
//             String emailDb = rs.getString(2);
                String passDb = rs.getString(3);

                admin = new Admin(id, email, passDb);
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


        logger.trace("Found " + " by email: " + email);
        return admin;
    }


    @Override
    public boolean update(Admin entity) {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ADMIN)){
            statement.setString(1,entity.getPass());
            statement.setString(2,entity.getEmail());
            result = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.trace("Password has been changed.");
        return result;
    }


//    public boolean delete(Admin entity) {
//        boolean result = false;
//        try (Connection connection = ConnectionPool.getConnection();
//        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ADMIN)){
//            statement.setString(1,entity.getEmail());
//            result = statement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        logger.trace("admin: " + entity.getEmail()+ " has been deleted");
//        return result;
//    }
}

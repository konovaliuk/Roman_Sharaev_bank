package roman.ironbank.model.dao.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {
    private static final String DATASOURCE_NAME = "jdbc/iron";
    private static DataSource dataSource;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());;
    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public ConnectionPool() {
    }


    public static Connection getConnection() throws SQLException{
        Connection connection = dataSource.getConnection();
        return connection;
    }
}

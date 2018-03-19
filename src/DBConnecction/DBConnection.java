
package DBConnecction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sandman
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection conn;
    
    private DBConnection()throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://localhost/thogakade","root","root");
    }
    public static DBConnection getDBConnection() throws ClassNotFoundException, SQLException{
        if(dbConnection==null){
            dbConnection= new DBConnection();
        }
        return dbConnection;
    }
    public Connection getConnection(){
        return conn;
    }
}

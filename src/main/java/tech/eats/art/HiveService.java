package tech.eats.art;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jhunter on 1/22/17.
 */
public class HiveService {

    private Connection con;
    private Statement statement;
    private String driverName = "org.apache.hive.jdbc.HiveDriver";

    public HiveService(){
        try{
            Class.forName(driverName);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Connection getConnection() throws SQLException {
        if(con != null){
            return con;
        }else{
            con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "jhunter", "");
            return con;
        }

    }

    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public void closeConnection() throws SQLException {
        if(con != null){
            con.close();
        }
    }
}

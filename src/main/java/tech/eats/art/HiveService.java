package tech.eats.art;

import java.sql.*;

/**
 * Created by jhunter on 1/22/17.
 */
public class HiveService {

    private Connection con;
    private Statement statement;
    private String driverName = "org.apache.hive.jdbc.HiveDriver";

    public HiveService() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Connection getConnection() throws SQLException {
        if (con != null) {
            return con;
        } else {
            con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "jhunter", "");
            return con;
        }
    }

    public ResultSet getWordCounts() throws SQLException {
        //Let mapreduce do most of the word count. Clean up later
        String sql = ("SELECT word,COUNT(1) AS count FROM (SELECT explode(split(text, '[ \\n]')) AS word FROM json_table)" +
                " tempTable GROUP BY word ORDER BY count DESC");
        return getStatement().executeQuery(sql);
    }

    public ResultSet getWords() throws SQLException {
        String sql = ("SELECT explode(split(text, '[ \\n]')) AS word FROM json_table");
        return getStatement().executeQuery(sql);
    }


    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public void closeConnection() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}

package tech.eats.art;

/**
 * Created by jhunter on 1/20/17.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.*;


public class Main {

    public static void main(String[] args) throws SQLException{
        Map<String, Integer> words = new HashMap<>();
        HiveService hService = new HiveService();

        Statement stmt = hService.getStatement();
        //Let mapreduce do most of the word count. Clean up later
        String sql = ("SELECT word,COUNT(1) AS count FROM (SELECT explode(split(text, '[ \\n]')) AS word FROM json_table)" +
                " tempTable GROUP BY word ORDER BY count DESC");
        ResultSet res = stmt.executeQuery(sql);

        String current = "";
        int currentCount = 0;

        while(res.next()){
            //Normalize word
            current = res.getString("word");
            currentCount = res.getInt("count");
            current = WordMapper.normalizeString(current);

            if(!words.containsKey(current)) {
                words.put(current,currentCount);
            }else{
                words.put(current, words.get(current) + currentCount);
            }
        }

        Iterator it = words.entrySet().iterator();

        /*try {
            PrintWriter pw = new PrintWriter(new File("word_counts.csv"));
            StringBuilder sb  = new StringBuilder();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                sb.append(pair.getKey());
                sb.append(",");
                sb.append(pair.getValue());
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        hService.closeConnection();
    }
}

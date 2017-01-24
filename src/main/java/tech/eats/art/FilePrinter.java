package tech.eats.art;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jhunter on 1/23/17.
 */
public class FilePrinter {

    public static void writeOutWordCounts(Map<String, Integer> words){

        Iterator it = words.entrySet().iterator();

        try {
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
        }
    }

    public static void writeOutFile(String theString, String fileName) throws SQLException {
        try{
            PrintWriter pw = new PrintWriter(new File(fileName));
            pw.write(theString);
            pw.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}

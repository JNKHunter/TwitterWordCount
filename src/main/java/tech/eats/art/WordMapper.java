package tech.eats.art;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by jhunter on 1/22/17.
 */
public class WordMapper {

    private static List<String> blacklist = Arrays.asList(new String[]{"the", "and", "for", "this", "from", "that", "when",
            "with", "did", "its", "it's", "but" });

    public static String normalizeString(String theString){
        theString = theString.toLowerCase();
        theString = theString.trim();
        theString = theString.replaceAll("^[ #().:;,!?+\"\']+", "");
        theString = theString.replaceAll("[ #().,:;!?+\"\']+$", "");
        theString = theString.replaceAll("(\\r|\\n)", "");

        return theString;
    }

    public static boolean isUsableWord(String theString){

        if(blacklist.contains(theString)){
            return false;
        }

        if(theString.startsWith("http")){
            return false;
        }

        if(theString.length() < 3){
            return false;
        }

        return true;
    }


    public static Map<String, Integer> mapWords(ResultSet res) throws SQLException {
        String current = "";
        int currentCount = 0;
        Map<String, Integer> words = new HashMap<>();

        while(res.next()){
            //Normalize word
            current = res.getString("word");
            currentCount = res.getInt("count");
            current = WordMapper.normalizeString(current);

            //Min length for a word to get saved into map
            if(isUsableWord(current)){
                if(!words.containsKey(current)) {
                    words.put(current,currentCount);
                }else{
                    words.put(current, words.get(current) + currentCount);
                }
            }
        }
        return words;
    }


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

    public static void writeOutFlatWords(ResultSet res) throws SQLException {
        try{
            PrintWriter pw = new PrintWriter(new File("words.txt"));
            StringBuilder sb = new StringBuilder();
            while(res.next()){
                if(isUsableWord(res.getString("word"))){
                    sb.append(WordMapper.normalizeString(res.getString("word")));
                    sb.append(" ");
                }

            }

            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}

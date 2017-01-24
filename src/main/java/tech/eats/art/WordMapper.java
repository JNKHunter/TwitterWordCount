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
            "with", "did", "its", "it's", "but", "are", "was", "&amp" });


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

    public static String resultSetToTokenString(ResultSet res, String token) throws SQLException {

        StringBuilder sb = new StringBuilder();
        String current = "";

        while (res.next()) {
            current = WordMapper.normalizeString(res.getString("word"));

            if (WordMapper.isUsableWord(current)) {
                sb.append(current);
                sb.append(token);
            }
        }

        return sb.toString();
    }

}

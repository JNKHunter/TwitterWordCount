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

    private static List<String> blacklist = Arrays.asList(new String[]{"the", "their", "and", "for", "this", "from", "that", "when",
            "with", "did", "would", "would've", "its", "it's", "i'm", "i've", "too", "i'd", "but", "are", "was", "does", "&amp", "inauguration", "they", "put"});


    private static int MIN_WORD_LENGTH = 3;

    public static String normalizeString(String theString) {
        theString = theString.toLowerCase();
        theString = theString.trim();
        theString = theString.replaceAll("^[ #().…:;,!?+\"\']+", "");
        theString = theString.replaceAll("[ #().…,:;!?+\"\']+$", "");
        theString = theString.replaceAll("(\\r|\\n)", "");

        return theString;
    }

    public static boolean isUsableWord(String theString, int minWordLength) {

        if (blacklist.contains(theString)) {
            return false;
        }

        if (theString.startsWith("http")) {
            return false;
        }

        if (theString.length() < minWordLength) {
            return false;
        }

        return true;
    }


    public static Map<String, Integer> mapWords(ResultSet res) throws SQLException {
        String current = "";
        int currentCount = 0;
        Map<String, Integer> words = new HashMap<>();

        while (res.next()) {
            //Normalize word
            current = res.getString("word");
            currentCount = res.getInt("count");
            current = WordMapper.normalizeString(current);

            //Min length for a word to get saved into map
            if (isUsableWord(current, MIN_WORD_LENGTH)) {
                if (!words.containsKey(current)) {
                    words.put(current, currentCount);
                } else {
                    words.put(current, words.get(current) + currentCount);
                }
            }
        }
        return words;
    }

    public static String mapToCsvString(Map<String, Integer> wordCounts) {
        Iterator it = wordCounts.entrySet().iterator();

        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            sb.append(pair.getKey());
            sb.append(",");
            sb.append(pair.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String resultSetToTokenString(ResultSet res, String token) throws SQLException {

        StringBuilder sb = new StringBuilder();
        String current = "";

        while (res.next()) {
            current = WordMapper.normalizeString(res.getString("word"));

            if (WordMapper.isUsableWord(current, MIN_WORD_LENGTH)) {
                sb.append(current);
                sb.append(token);
            }
        }

        return sb.toString();
    }

}

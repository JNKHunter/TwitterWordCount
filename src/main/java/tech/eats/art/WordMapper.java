package tech.eats.art;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jhunter on 1/22/17.
 */
public class WordMapper {


    Map<String, Integer> words = new HashMap<>();


    public static String normalizeString(String theString){
        theString = theString.toLowerCase();
        theString = theString.replaceAll("^[ @\"\']", "");
        theString = theString.replaceAll("(\\r|\\n)", "");

        return theString;
    }
}

package tech.eats.art;

/**
 * Created by jhunter on 1/20/17.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws SQLException{
        Map<String, Integer> words = new HashMap<>();
        HiveService hService = new HiveService();

        ResultSet res = hService.getWordCounts();
        words = WordMapper.mapWords(res);

        WordMapper.writeOutWordCounts(words);

        hService.closeConnection();
    }
}

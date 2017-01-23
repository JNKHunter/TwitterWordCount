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

        ResultSet res = hService.getWordCounts();
        words = WordMapper.mapWords(res);

        WordMapper.writeOutWordCounts(words);

        hService.closeConnection();
    }
}

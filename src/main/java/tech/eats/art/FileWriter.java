package tech.eats.art;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by jhunter on 1/23/17.
 */
public class FileWriter {

    public static void writeOutFile(String theString, String fileName) {
        try{
            PrintWriter pw = new PrintWriter(new File(fileName));
            pw.write(theString);
            pw.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}

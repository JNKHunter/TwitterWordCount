package tech.eats.art;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jhunter on 1/22/17.
 */
public class MainFlatFile {
    public static void main(String[] args) throws SQLException, IOException {
        HiveService hService = new HiveService();
        String words = WordMapper.resultSetToTokenString(hService.getWords(), " ");
        FileWriter.writeOutFile(words, "words.txt");
        hService.closeConnection();

        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(1200);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("words.txt");
        final Dimension dimension = new Dimension(1080, 1080);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(new ColorPalette(new Color(0xE65100), new Color(0xEF6C00),
                new Color(0xF57C00), new Color(0xFB8C00), new Color(0xFF9800), new Color(0xFFA726)));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 100));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("wordcloud_rectangle.png");
    }

}

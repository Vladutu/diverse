package ro.ucv.ace;

import ro.ucv.ace.sentiment_analysis.SentimentAnalyzer;

/**
 * Created by Geo on 26.06.2017.
 */
public class App2 {

    public static void main(String[] args) {
        String text = "Love this beast.";
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        System.out.println(sentimentAnalyzer.computePolarity(text));
    }
}

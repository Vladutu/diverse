package ro.ucv.ace;

import ro.ucv.ace.sentiment_analysis.SentimentAnalyzer;

/**
 * Created by Geo on 26.06.2017.
 */
public class App2 {

    public static void main(String[] args) {
        String text = "Great Item";
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        sentimentAnalyzer.computePolarity(text);
    }
}

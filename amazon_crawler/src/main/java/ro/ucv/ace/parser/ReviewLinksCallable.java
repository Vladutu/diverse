package ro.ucv.ace.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Geo on 28.12.2016.
 */
public class ReviewLinksCallable implements Callable<List<String>> {

    private String url;

    private String userAgent;

    private int timeout;

    public ReviewLinksCallable(String url, String userAgent, int timeout) {
        this.url = url;
        this.userAgent = userAgent;
        this.timeout = timeout;
    }

    @Override
    public List<String> call() throws Exception {
        Document reviewBodyPage = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get();
        Elements reviewsLinks = reviewBodyPage.select("a.review-title");
        List<String> reviewBodyUrls = new ArrayList<>();
        reviewsLinks.forEach(rl -> {
            reviewBodyUrls.add("https://amazon.com" + rl.attr("href"));
        });

        return reviewBodyUrls;
    }
}

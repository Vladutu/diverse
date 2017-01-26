package ro.ucv.ace.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

/**
 * Created by Geo on 25.01.2017.
 */
public class HtmlPageCallable implements Callable<Document> {

    private String url;

    private String userAgent;

    public HtmlPageCallable(String url, String userAgent) {
        this.url = url;
        this.userAgent = userAgent;
    }

    @Override
    public Document call() throws Exception {
        Document document = Jsoup.connect(url).userAgent(userAgent).get();
        System.out.println("Downloaded " + url);
        return document;
    }
}

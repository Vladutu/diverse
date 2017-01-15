package ro.ucv.ace.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Geo on 27.12.2016.
 */
public class ProductLinksCallable implements Callable<List<String>> {

    private String url;

    private String userAgent;

    private int timeout;

    public ProductLinksCallable(String url, String userAgent, int timeout) {
        this.url = url;
        this.userAgent = userAgent;
        this.timeout = timeout;
    }


    @Override
    public List<String> call() throws Exception {
        Document page = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get();

        Elements links = page.select("div.s-item-container a.a-link-normal.s-access-detail-page.a-text-normal");
        List<String> result = new ArrayList<>();
        for (Element link : links) {
            String productUrl = link.attr("href");
            if (productUrl.startsWith("https")) {
                result.add(productUrl);
                //   break;
            }
        }


        return result;
    }
}

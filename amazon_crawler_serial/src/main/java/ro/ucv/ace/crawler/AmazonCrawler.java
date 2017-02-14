package ro.ucv.ace.crawler;

/**
 * Created by Geo on 11.02.2017.
 */
public interface AmazonCrawler {

    void crawlAndSaveProducts(String categoryUrl, int fromPage, int toPage, String categoryName, int maxProducts);

    void crawlAndSaveProductsReviews(int fromId, int fromPage);
}

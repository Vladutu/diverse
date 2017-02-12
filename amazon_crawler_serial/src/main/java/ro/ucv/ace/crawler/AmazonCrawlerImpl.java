package ro.ucv.ace.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ro.ucv.ace.api.AmazonApi;
import ro.ucv.ace.downloader.JsoupDownloader;
import ro.ucv.ace.entity.Category;
import ro.ucv.ace.entity.Product;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.parser.ReviewParser;
import ro.ucv.ace.service.CategoryService;
import ro.ucv.ace.service.ProductService;

import java.util.List;

/**
 * Created by Geo on 11.02.2017.
 */
@Component
public class AmazonCrawlerImpl implements AmazonCrawler, DisposableBean {

    @Autowired
    private JsoupDownloader jsoupDownloader;

    @Autowired
    private AmazonApi amazonApi;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewParser reviewParser;

    private WebDriver driver;

    private final static Logger logger = LoggerFactory.getLogger(AmazonCrawlerImpl.class);

    public AmazonCrawlerImpl() {
        driver = new ChromeDriver();
    }

    @Override
    public void crawlAndSaveProducts(String categoryUrl, int fromPage, int toPage, String categoryName) {
        logger.info("Stating category page: " + categoryUrl);

        for (int i = fromPage; i <= toPage; i++) {
            String pageUrl = categoryUrl + i;
            logger.info("On page " + pageUrl);
            Document document = jsoupDownloader.download(pageUrl);
            Elements productPanels = document.select("div.s-item-container");

            for (Element productPanel : productPanels) {
                String url = getUrl(productPanel);
                if (url.contains("/slredirect/")) {
                    continue;
                }

                String asin = getAsin(url);
                String title = getTitle(productPanel);
                String rawData = amazonApi.productInfo(asin);

                logger.info("Saving product with ASIN: " + asin);
                buildAndSaveProduct(url, asin, title, rawData, categoryName);
            }
        }
    }

    @Override
    public void crawlAndSaveProductsReviews(int fromId, int fromPage) {
        List<Integer> productIds = productService.getProductsIds();
        int index = 0;
        while (productIds.get(index) != fromId) {
            index++;
        }

        for (int i = index; i < productIds.size(); i++) {
            Product product = productService.getProduct(productIds.get(i));
            String primaryReviewUrl = product.getPrimaryReviewUrl();
            int noReviewPages = findReviewPagesNumber(primaryReviewUrl);

            for (int pageNo = fromPage; pageNo <= noReviewPages; pageNo++) {
                List<Review> reviews = findReviewsOnPage(product.getId(), primaryReviewUrl, pageNo);
            }
        }

    }

    private List<Review> findReviewsOnPage(Integer id, String primaryReviewUrl, int pageNo) {
        String url = primaryReviewUrl + pageNo;

        driver.get(url);
        while (driver.getPageSource().contains("Sorry, we just need to make sure you're not a robot.")) {
            driver.navigate().refresh();
            sleep(500);
        }

        List<WebElement> elements = driver.findElements(By.cssSelector("a[class='a-expander-header a-declarative a-expander-inline-header a-link-expander']"));
        for (WebElement element : elements) {
            if (!element.getText().equals("Replay")) {
                element.click();
                sleep(400);
            }
        }
        sleep(400);

        int size = 0;
        do {
            List<WebElement> moreCommentsElements = driver.findElements(By.cssSelector("span[class='a-button a-button-small more-comments-button']"));
            size = moreCommentsElements.size();
            moreCommentsElements.forEach(e -> {
                e.click();
                sleep(400);
            });
            sleep(1000);

        } while (size > 0);

        List<WebElement> replays = driver.findElements(By.xpath("//span[@class='a-declarative']/a[text()='an earlier post']"));
        WebElement totalReviewCount = driver.findElement(By.cssSelector("span[class='a-size-medium totalReviewCount']"));

        replays.forEach(replay -> {
            try {
                replay.click();
            } catch (Exception e) {
                sleep(1000);
                replay.click();
            }
            sleep(150);
            totalReviewCount.click();
            sleep(250);
        });


        return reviewParser.parse(driver.getPageSource());
    }


    private int findReviewPagesNumber(String primaryReviewUrl) {
        String firstPage = primaryReviewUrl + "1";
        Document document = jsoupDownloader.download(firstPage);
        sleep(2000);
        Element pagination = document.select("div#cm_cr-pagination_bar").first();
        if (pagination == null) {
            return 1;
        }
        Element last = pagination.select("li.page-button").last();
        if (last == null) {
            return 1;
        }

        return Integer.parseInt(last.text());
    }


    @Override
    public void destroy() throws Exception {
        driver.quit();
    }

    private void buildAndSaveProduct(String url, String asin, String title, String rawData, String categoryName) {
        Category category = categoryService.findByName(categoryName);
        Product product = new Product(asin, title, url, rawData, category);
        try {
            productService.save(product);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getMessage().contains("Duplicate entry")) {
                logger.info("Product with ASIN: " + asin + " already exists");
            } else {
                throw e;
            }
        }
        sleep(800);
    }

    private void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getTitle(Element productPanel) {
        Element link = productPanel.select("a.a-link-normal.s-access-detail-page.a-text-normal").first();

        return link.attr("title");
    }

    private String getUrl(Element productPanel) {
        Element link = productPanel.select("a.a-link-normal.s-access-detail-page.a-text-normal").first();
        String url = link.attr("href");

        int position = url.indexOf("/ref=");
        url = url.substring(0, position);

        return url;
    }

    private String getAsin(String url) {
        return url.substring(url.indexOf("/dp/") + 4, url.length());
    }


}

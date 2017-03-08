package ro.ucv.ace.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import ro.ucv.ace.exception.InvalidClickException;
import ro.ucv.ace.parser.ReviewParser;
import ro.ucv.ace.service.AuthorService;
import ro.ucv.ace.service.CategoryService;
import ro.ucv.ace.service.ProductService;
import ro.ucv.ace.service.ReviewService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ReviewService reviewService;

    private WebDriver driver;

    private final static Logger logger = LoggerFactory.getLogger(AmazonCrawlerImpl.class);

    public AmazonCrawlerImpl() {
        driver = new ChromeDriver();
    }

    @Override
    public void crawlAndSaveProducts(String categoryUrl, int fromPage, int toPage, String categoryName, int maxProducts) {
        logger.info("Stating category page: " + categoryUrl);

        for (int i = fromPage; i <= toPage; i++) {
            String pageUrl = categoryUrl + i;
            logger.info("On page " + pageUrl);
            Document document = jsoupDownloader.download(pageUrl);
            Elements productPanels = document.select("div.s-item-container");

            for (Element productPanel : productPanels) {
                int productCount = categoryService.getProductsNumber(categoryName);
                if (productCount >= maxProducts) {
                    return;
                }

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
    public void crawlAndSaveProductsReviews(int fromId, int fromPage, List<Integer> excludes) {
        logger.info("Starting to crawl reviews from product with id " + fromId + " and review page with no: " + fromPage);
        List<Integer> allProductIds = productService.getProductsIds();
        boolean firstTime = true;

        List<Integer> goodIds = allProductIds.stream().filter(id -> id >= fromId && !excludes.contains(id)).collect(Collectors.toList());
        Collections.sort(goodIds);


        for (Integer id : goodIds) {
            Product product = productService.getProduct(id);
            String primaryReviewUrl = product.getPrimaryReviewUrl();
            int noReviewPages = findReviewPagesNumber(primaryReviewUrl);

            if (!firstTime) {
                fromPage = 1;
            }

            for (int pageNo = fromPage; pageNo <= noReviewPages; pageNo++) {
                logger.info("Crawling reviews for product with id " + product.getId() + ". Currently at page " + pageNo + "/" + noReviewPages + " of all reviews");
                List<Review> reviews = findReviewsOnPage(product.getId(), primaryReviewUrl, pageNo);
                Double productOverallRating = reviewParser.getProductOverallRating(driver.getPageSource());

                logger.info("Saving reviews for product with id " + product.getId() + ". Currently at page " + pageNo + "/" + noReviewPages + " of all reviews");
                productService.saveReviewsAndOverallRating(product.getId(), reviews, productOverallRating);
            }

            firstTime = false;
        }

    }

    @Override
    public void testSent() {
        driver.get("http://www.paralleldots.com/sentiment-analysis");
        WebElement input = driver.findElement(By.id("se1"));
        WebElement button = driver.findElement(By.id("btn-sen"));

        for (int i = 1; i <= 10; i++) {
            Review byId = reviewService.getById(i);

            input.clear();
            input.sendKeys(byId.getBody());
            button.click();
            sleep(2000);
        }
    }

    private List<Review> findReviewsOnPage(Integer id, String primaryReviewUrl, int pageNo) {
        String url = primaryReviewUrl + pageNo;
        boolean success = false;

        do {
            try {
                findReviewsOnPage(url);
                success = true;
            } catch (Exception e) {
                logger.info("Refreshing page...");
                driver.navigate().refresh();
            }
        } while (!success);

        return reviewParser.parse(driver.getPageSource());
    }

    private void findReviewsOnPage(String url) {
        driver.get(url);
        resolveCaptchaPage();

        // Click on button to see review replays
        List<WebElement> elements = driver.findElements(By.cssSelector("a[class='a-expander-header a-declarative a-expander-inline-header a-link-expander']"));
        for (WebElement element : elements) {
            if (!element.getText().equals("Comment")) {
                clickElement(element);
                sleep(500);
                waitForJSandJQueryToLoad();
            }
        }
        sleep(500);

        int size = 0;
        do {
            List<WebElement> moreCommentsElements = driver.findElements(By.cssSelector("span[class='a-button a-button-small more-comments-button']"));
            size = moreCommentsElements.size();
            moreCommentsElements.forEach(e -> {
                clickElement(e);
                sleep(300);
                waitForJSandJQueryToLoad();
            });
            waitForJSandJQueryToLoad();
            sleep(1000);

        } while (size > 0);

        List<WebElement> replays = driver.findElements(By.xpath("//span[@class='a-declarative']/a[text()='an earlier post']"));
        WebElement totalReviewCount = driver.findElement(By.cssSelector("span[class='a-size-medium totalReviewCount']"));

        //click all "an earlier post"
        replays.forEach(replay -> {
            int count = 0;
            boolean exception = false;
            do {
                exception = false;
                try {
                    replay.click();
                } catch (Exception e) {
                    sleep(500);
                    totalReviewCount.click();
                    exception = true;
                    count++;
                }
                if (count == 20) {
                    logger.error("Could not click an earlier post");
                    throw new InvalidClickException("Could not click an earlier post (exiting)");
                }
            } while (exception);
            waitForJSandJQueryToLoad();
            sleep(150);
            totalReviewCount.click();
            sleep(150);
        });
    }

    private void resolveCaptchaPage() {
        int captchaCount = 0;
        while (driver.getPageSource().contains("Sorry, we just need to make sure you're not a robot.")) {
            driver.navigate().refresh();
            captchaCount++;
            sleep(700);

            if (captchaCount == 5) {
                captchaCount = 0;
                try {
                    System.out.println("Please enter a character to continue:");
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clickElement(WebElement e) {
        boolean successClick = false;
        int clickCount = 0;
        do {
            try {
                e.click();
                successClick = true;
            } catch (WebDriverException ex) {
                clickCount++;
                sleep(300);
                if (clickCount == 10) {
                    logger.warn("Could not see the element needed to click.");
                    throw new InvalidClickException("Could not see the element needed to click.");
                }
            }
        } while (!successClick);
    }


    private int findReviewPagesNumber(String primaryReviewUrl) {
        String firstPage = primaryReviewUrl + "1";

        driver.get(firstPage);
        resolveCaptchaPage();

        try {
            WebElement pagination = driver.findElement(By.id("cm_cr-pagination_bar"));
            List<WebElement> pages = pagination.findElements(By.cssSelector("li[class='page-button']"));
            if (pages.isEmpty()) {
                return 1;
            }
            String no = pages.get(pages.size() - 1).getText();
            no = no.replaceAll(",", "").replaceAll("\\.", "");
            return Integer.parseInt(no);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return 1;
        }
    }

    public boolean waitForJSandJQueryToLoad() {

        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
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

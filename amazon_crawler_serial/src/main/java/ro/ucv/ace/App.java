package ro.ucv.ace;

import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.api.AmazonApi;
import ro.ucv.ace.config.SpringConfiguration;
import ro.ucv.ace.crawler.AmazonCrawler;
import ro.ucv.ace.downloader.JsoupDownloader;
import ro.ucv.ace.downloader.SerialJsoupDownloader;
import ro.ucv.ace.entity.Category;
import ro.ucv.ace.service.CategoryService;
import ro.ucv.ace.service.ProductService;

/**
 * Hello world!
 */
@Component
public class App {

    @Autowired
    private AmazonApi amazonApi;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AmazonCrawler amazonCrawler;

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\ChromeDriver\\chromedriver.exe");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        App app = ctx.getBean(App.class);

        //app.crawlProducts();
        // app.testDownloader();
        //app.addCategory();
        //app.saveCategory();
        //app.parseProducts();
        //app.testSelenium();
        app.getProductReviews();

    }

    private void getProductReviews() {
        amazonCrawler.crawlAndSaveProductsReviews(12, 226);
    }

    private void testSelenium() {
        WebDriver driver = new ChromeDriver();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver.get("http://reddit.com/r/dota2");
        //System.out.println(driver.getTitle());
        driver.quit();
    }


    private void addCategory() {
        categoryService.addTraditionalLaptops();
    }

    private void parseProducts() {
        productService.parseRawData();
    }

    private void saveCategory() {
        Category amazon = new Category("Amazon", null);
        Category electronics = new Category("Electronics", amazon);
        Category compAndAcc = new Category("Computers & Accessories", electronics);
        Category monitors = new Category("Monitors", compAndAcc);

        Category save = categoryService.save(monitors);
        System.out.println(save);
    }

    private void crawlProducts() {
        amazonCrawler.crawlAndSaveProducts("https://www.amazon.com/b/ref=lp_565108_ln_1?node=13896615011&ie" +
                "=UTF8&qid=1486837737&page=", 40, 100, "Traditional Laptops");

    }

    private void testDownloader() {
        JsoupDownloader jsoupDownloader = new SerialJsoupDownloader();
        for (int i = 1; i <= 248; i++) {
            String url = "https://www.amazon.com/Monitors-Computers-Accessories/b/ref=nav_shopall_monitors?ie=UTF8&node=1292115011&page=" + i;
            Document document = jsoupDownloader.download(url);
            System.out.println(document.location());
        }
    }
}

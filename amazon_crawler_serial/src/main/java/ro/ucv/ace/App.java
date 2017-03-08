package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.SpringConfiguration;
import ro.ucv.ace.crawler.AmazonCrawler;
import ro.ucv.ace.service.ProductService;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
@Component
public class App {

    @Autowired
    private AmazonCrawler amazonCrawler;

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\ChromeDriver\\chromedriver.exe");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        App app = ctx.getBean(App.class);

        //app.getProductReviews();
        app.amazonCrawler.testSent();
        //app.crawlProducts();
        //app.categoryService.addCategory();
        //app.productService.parseRawData(6162, 6223);
    }

    private void getProductReviews() {
        List<Integer> excludes = Arrays.asList(5623, 5624, 5625, 5626, 5627);
        amazonCrawler.crawlAndSaveProductsReviews(5909, 25, excludes);
    }


    private void crawlProducts() {
        amazonCrawler.crawlAndSaveProducts("https://www.amazon.com/" +
                "b?node=13896615011&ie=UTF8&qid=1487089479&page=", 1, 20, "Traditional Laptops", 100);

    }
}

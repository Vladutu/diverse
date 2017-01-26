package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.MyConfiguration;
import ro.ucv.ace.downloader.JsoupDownloader;
import ro.ucv.ace.service.CategoryService;
import ro.ucv.ace.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
@Component
public class App {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JsoupDownloader jsoupDownloader;

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
        App app = ctx.getBean(App.class);
        app.action();
    }

    public void action() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("product_links.txt").getFile());
        List<String> userAgents = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                jsoupDownloader.download(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

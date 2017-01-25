package ro.ucv.ace.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.ucv.ace.entity.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Geo on 27.12.2016.
 */
@Component
public class ProductParserImpl implements InitializingBean, DisposableBean, ProductParser {

    private ExecutorService executorService;

    @Value("${crawler.userAgent}")
    private String USER_AGENT;

    @Value("${crawler.timeout}")
    private int TIMEOUT;

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
    }

    @Override
    public List<String> parseCategoryPage(String url) {
        List<String> productLinks = new ArrayList<>();
        System.out.println("Starting category...");
        try {
            Document page = downloadPage(url);
            int noProductPages = findNoProductPages(page);

            List<Callable<List<String>>> callables = new ArrayList<>();

            //TODO: replace 1 with noProductPages
            for (int i = 1; i <= noProductPages; i++) {
                String pagedUrl = url + "&page=" + i;
                callables.add(new ProductLinksCallable(pagedUrl, USER_AGENT, TIMEOUT));
            }

            List<Future<List<String>>> futures = executorService.invokeAll(callables);
            futures.forEach(future -> {
                try {
                    productLinks.addAll(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Category finished.");
        return productLinks;
    }

    @Override
    public List<Product> parseProductPages(List<String> urls) {
        List<Product> products = new ArrayList<>();
        // List<Future<Product>> futures = new ArrayList<>();

        urls.forEach(url -> {
//            Future<Product> submit = executorService.submit(new ProductCallable(url, USER_AGENT, TIMEOUT, executorService));
            // futures.add(submit);

            try {
                products.add(executorService.submit(new ProductCallable(url, USER_AGENT, TIMEOUT, executorService)).get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

//        futures.forEach(future -> {
//            try {
//                products.add(future.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        });

        return products;
    }


    private int findNoProductPages(Document page) {
        Element pageBar = page.select("div.pagnHy span.pagnDisabled").first();

        return Integer.parseInt(pageBar.text());
    }

    private Document downloadPage(String url) throws IOException {
        return Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT).get();
    }

}

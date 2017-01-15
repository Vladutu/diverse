package ro.ucv.ace.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.ucv.ace.entity.Category;
import ro.ucv.ace.entity.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Geo on 27.12.2016.
 */
public class ProductCallable implements Callable<Product> {

    private String url;

    private String userAgent;

    private int timeout;

    private ExecutorService executorService;

    public ProductCallable(String url, String userAgent, int timeout, ExecutorService executorService) {
        this.url = url;
        this.userAgent = userAgent;
        this.timeout = timeout;
        this.executorService = executorService;
    }

    @Override
    public Product call() throws Exception {
        Document page = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get();

        Category category = createCategoryPath(page);
        String name = findProductName(page);
        float rating = findProductRating(page);
        String asin = findAsin(page);
        //List<String> reviews = searchReviews(url);


        return new Product(name, asin, rating, category);
    }

    private List<String> searchReviews(String url) throws IOException {
        String reviewUrl = buildReviewUrl(url);
        Document reviewsPage = Jsoup.connect(reviewUrl + 1).userAgent(userAgent).timeout(timeout).get();
        int reviewsPageNo = 0;
        try {
            reviewsPageNo = Integer.parseInt(reviewsPage.select("div#cm_cr-pagination_bar li.page-button").last().text());
        } catch (NullPointerException e) {
            reviewsPageNo = 1;
        }
        List<String> reviewBodyUrls = new ArrayList<>();
        List<Future<List<String>>> futures = new ArrayList<>();
        for (int i = 1; i <= reviewsPageNo; i++) {

            futures.add(executorService.submit(new ReviewLinksCallable(reviewUrl + i, userAgent, timeout)));
        }

        futures.forEach(future -> {
            try {
                reviewBodyUrls.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        reviewBodyUrls.forEach(System.out::println);

        return null;
    }

    private String buildReviewUrl(String url) {
        String reviewUrl = url.replace("/dp/", "/product-reviews/");
        int index = reviewUrl.lastIndexOf("/");
        reviewUrl = reviewUrl.substring(0, index);
        reviewUrl += "?reviewerType=all_reviews&sortBy=recent&pageNumber=";

        return reviewUrl;
    }

    private String findAsin(Document page) {
        Elements tds = page.select("div#productDetails_db_sections tr td");

        return tds.get(0).text();
    }

    private float findProductRating(Document page) {
        Elements tds = page.select("div#productDetails_db_sections tr td");
        String text = tds.get(1).text();
        //float rating = Float.parseFloat(text.split(" ")[0]);

        return 0;
    }

    private Category createCategoryPath(Document page) {
        List<String> categ = findCategories(page);
        List<Category> categories = new ArrayList<>();
        categ.forEach(c -> categories.add(new Category(c)));

        for (int i = categories.size() - 2; i >= 0; i--) {
            Category parent = categories.get(i);
            Category child = categories.get(i + 1);

            parent.addChildren(child);
            child.setParent(parent);
        }
        Category amazon = new Category("Amazon");
        categories.get(0).setParent(amazon);
        amazon.addChildren(categories.get(0));

        return categories.get(categories.size() - 1);
    }


    private String findProductName(Document page) {
        Element first = page.select("span#productTitle").first();
        return first.text();
    }


    private List<String> findCategories(Document page) {
        List<String> categories = new ArrayList<>();
        Elements categElems = page.select("a.a-link-normal.a-color-tertiary");
        categElems.forEach(categ -> {
            String text = categ.text();
            categories.add(text);
        });

        return categories;
    }
}

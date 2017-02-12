package ro.ucv.ace.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ro.ucv.ace.entity.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 12.02.2017.
 */
@Component
public class ReviewParserImpl implements ReviewParser {

    @Override
    public List<Review> parse(String source) {
        List<Review> reviews = new ArrayList<>();
        Document document = Jsoup.parse(source);
        Elements reviewElems = document.select("div.a-section.review");

        for (Element reviewElem : reviewElems) {
            Review review = parse(reviewElem);
            reviews.add(review);
        }

        return reviews;
    }

    private Review parse(Element reviewElem) {
        Double rating = getReviewRating(reviewElem);
        String title = getReviewTitle(reviewElem);

        //TODO: Build review and return it
        return null;
    }

    private String getReviewTitle(Element reviewElem) {
        Element select = reviewElem.select("a.review-title").first();
        if (select != null) {
            return select.text();
        }

        return null;
    }

    private Double getReviewRating(Element reviewElem) {
        Element select = reviewElem.select("i.a-icon.a-icon-star.review-rating").first();
        if (select != null) {
            String str = select.text();
            return Double.valueOf(str.split(" ")[0]);
        }

        return null;
    }
}

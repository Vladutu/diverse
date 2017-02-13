package ro.ucv.ace.parser;

import ro.ucv.ace.entity.Review;

import java.util.List;

/**
 * Created by Geo on 12.02.2017.
 */
public interface ReviewParser {

    List<Review> parse(String source);

    Double getProductOverallRating(String source);
}

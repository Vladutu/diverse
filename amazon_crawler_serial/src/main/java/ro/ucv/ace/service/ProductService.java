package ro.ucv.ace.service;

import ro.ucv.ace.entity.Product;
import ro.ucv.ace.entity.Review;

import java.util.List;

/**
 * Created by Geo on 11.02.2017.
 */
public interface ProductService {

    Product save(Product product);

    void parseRawData(int startId, int endId);

    void parseRawData();

    List<Integer> getProductsIds();

    Product getProduct(int id);

    void saveReviewsAndOverallRating(Integer id, List<Review> reviews, Double productOverallRating);
}

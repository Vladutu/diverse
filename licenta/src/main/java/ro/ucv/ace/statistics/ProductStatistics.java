package ro.ucv.ace.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Feature;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geo on 24.03.2017.
 */
@Component
@Transactional(readOnly = true)
public class ProductStatistics {

    @Autowired
    private ProductRepository productRepository;

    public Map<String, List<Integer>> countFeaturesInProductsGroupedByCategory() {
        Map<String, List<Integer>> map = new HashMap<>();

        productRepository.getAll().forEach(product -> {
            System.out.println("Computing product with id " + product.getId());
            String category = product.getCategory().getName();
            map.computeIfAbsent(category, k -> new ArrayList<>());

            List<Feature> features = product.getFeatures();
            map.get(category).add(features.size());
        });

        return map;
    }

    public Map<String, List<Double>> averageReviewLengthAndProductPrice() {
        Map<String, List<Double>> map = new HashMap<>();
        map.put("price", new ArrayList<>());
        map.put("length", new ArrayList<>());

        productRepository.getAll()
                .filter(product -> product.getPrice() != null)
                .forEach(product -> {
                    System.out.println("Computing product with id " + product.getId());
                    List<Review> reviews = product.getReviews();
                    int totalLength = reviews.stream().mapToInt(review -> review.getBody().length()).sum();

                    double avgLength = 0;
                    if (reviews.size() != 0) {
                        avgLength = totalLength / reviews.size();
                    }

                    map.get("price").add(product.getPrice());
                    map.get("length").add(avgLength);
                });

        return map;
    }
}

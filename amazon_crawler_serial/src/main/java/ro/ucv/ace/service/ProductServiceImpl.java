package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import ro.ucv.ace.entity.Author;
import ro.ucv.ace.entity.Product;
import ro.ucv.ace.entity.Replay;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.parser.ProductDataParser;
import ro.ucv.ace.repository.AuthorRepository;
import ro.ucv.ace.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

/**
 * Created by Geo on 11.02.2017.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDataParser productDataParser;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void parseRawData(int startId, int endId) {
        for (int i = startId; i <= endId; i++) {
            Product product = productRepository.findOne(i);
            if (product == null) {
                continue;
            }

            try {
                product.parse(productDataParser);
                System.out.println("Successfully parsed raw data for product with id:" + product.getId());
            } catch (IOException | SAXException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void parseRawData() {
        productRepository.findAllAndStream()
                .forEach(product -> {
                    try {
                        product.parse(productDataParser);
                    } catch (IOException | SAXException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public List<Integer> getProductsIds() {
        return productRepository.findAllIds();
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findOne(id);
    }

    @Override
    public void saveReviewsAndOverallRating(Integer id, List<Review> reviews, Double productOverallRating) {
        Product product = productRepository.findOne(id);

        for (Review review : reviews) {
            Author author = authorRepository.findByAmazonId(review.getAuthor().getAmazonId());
            if (author == null) {
                author = authorRepository.save(review.getAuthor());
            }
            review.setAuthor(author);

            checkAuthorsForReplays(review.getReplays());
        }

        product.addReviews(reviews);
        product.updateRating(productOverallRating);
    }

    private void checkAuthorsForReplays(List<Replay> replays) {
        for (Replay replay : replays) {
            Author author = authorRepository.findByAmazonId(replay.getAuthor().getAmazonId());
            if (author == null) {
                author = authorRepository.save(replay.getAuthor());
            }
            replay.setAuthor(author);

            checkAuthorsForReplays(replay.getReplays());
        }
    }


}

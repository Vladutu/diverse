package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import ro.ucv.ace.crawler.AmazonCrawler;
import ro.ucv.ace.entity.Product;
import ro.ucv.ace.parser.ProductDataParser;
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
    private AmazonCrawler amazonCrawler;

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


}

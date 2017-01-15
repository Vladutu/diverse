package ro.ucv.ace.parser;

import ro.ucv.ace.entity.Product;

import java.util.List;

/**
 * Created by Geo on 27.12.2016.
 */
public interface ProductParser {

    List<String> parseCategoryPage(String url);

    List<Product> parseProductPages(List<String> urls);
}

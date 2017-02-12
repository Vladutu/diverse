package ro.ucv.ace.parser;

import org.xml.sax.SAXException;
import ro.ucv.ace.entity.Product;

import java.io.IOException;

/**
 * Created by Geo on 11.02.2017.
 */
public interface ProductDataParser {

    void parse(Product product) throws IOException, SAXException;
}

package ro.ucv.ace.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ro.ucv.ace.entity.Feature;
import ro.ucv.ace.entity.Product;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 12.02.2017.
 */
@Component
public class ProductDataParserImpl implements ProductDataParser {

    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

    public ProductDataParserImpl() throws ParserConfigurationException {
    }

    @Override
    public void parse(Product product) throws IOException, SAXException {
        Document doc = documentBuilder.parse(new InputSource(new ByteArrayInputStream(product.getRawData().getBytes("utf-8"))));
        doc.getDocumentElement().normalize();

        NodeList itemLink = doc.getElementsByTagName("ItemLink");

        for (int i = 0; i < itemLink.getLength(); i++) {
            Node node = itemLink.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String description = element.getElementsByTagName("Description").item(0).getTextContent();
                String url = element.getElementsByTagName("URL").item(0).getTextContent();

                if (description.equals("All Customer Reviews")) {
                    product.setReviewsUrl(url);
                    break;
                }
            }
        }

        Node node = doc.getElementsByTagName("SalesRank").item(0);
        if (node != null) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element salesRank = (Element) node;
                Integer rank = Integer.valueOf(salesRank.getTextContent());
                product.setRank(rank);
            }
        }

        NodeList featureNodeList = doc.getElementsByTagName("Feature");
        List<Feature> features = new ArrayList<>();
        for (int i = 0; i < featureNodeList.getLength(); i++) {
            node = featureNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Feature feature = new Feature(element.getTextContent());
                features.add(feature);
            }
        }
        product.setFeatures(features);

        node = doc.getElementsByTagName("ListPrice").item(0);
        if (node != null) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element listPrice = (Element) node;
                Double price = Double.valueOf(listPrice.getElementsByTagName("Amount").item(0).getTextContent());
                price = price / 100;

                product.setPrice(price);
            }
        }

    }

}

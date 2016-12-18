package ro.ucv.ace;

import ro.ucv.ace.auction.Buyer;
import ro.ucv.ace.auction.DoubleAuction;
import ro.ucv.ace.auction.Product;
import ro.ucv.ace.auction.Seller;
import ro.ucv.ace.factory.BuyerFactory;
import ro.ucv.ace.factory.SellerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Product product = new Product("gold");
        BuyerFactory buyerFactory = new BuyerFactory();
        SellerFactory sellerFactory = new SellerFactory();

        List<Buyer> buyers = new ArrayList<Buyer>();
        List<Seller> sellers = new ArrayList<Seller>();

        for (int i = 1; i <= 10; i++) {
            buyers.add(buyerFactory.createBuyer(product));
            sellers.add(sellerFactory.createSeller(product));
        }

        DoubleAuction auction = new DoubleAuction(buyers,sellers);
        auction.start();
    }
}

package ro.ucv.ace.factory;

import ro.ucv.ace.auction.Offer;
import ro.ucv.ace.auction.Product;
import ro.ucv.ace.auction.Seller;

import java.util.Random;

/**
 * Created by Geo on 18.12.2016.
 */
public class SellerFactory {

    private Random random = new Random();

    public Seller createSeller(Product product) {
        float rand = random.nextInt() % 30 + 1;
        float value = 150 + rand;
        Offer offer = new Offer(product, value);

        return new Seller(offer);
    }
}

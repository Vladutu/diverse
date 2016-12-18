package ro.ucv.ace.factory;

import ro.ucv.ace.auction.Buyer;
import ro.ucv.ace.auction.Offer;
import ro.ucv.ace.auction.Product;

import java.util.Random;

/**
 * Created by Geo on 18.12.2016.
 */
public class BuyerFactory {

    private Random random = new Random();

    public Buyer createBuyer(Product product) {
        float rand = random.nextInt() % 30 + 1;
        float value = 140 + rand;
        Offer offer = new Offer(product, value);

        return new Buyer(offer);
    }
}

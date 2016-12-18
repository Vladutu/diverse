package ro.ucv.ace.auction;

/**
 * Created by Geo on 18.12.2016.
 */
public class Buyer {

    private Offer offer;

    public Buyer(Offer offer) {
        this.offer = offer;
    }

    public float getOfferPrice() {
        return offer.getPrice();
    }
}

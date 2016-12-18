package ro.ucv.ace.auction;

/**
 * Created by Geo on 18.12.2016.
 */
public class Seller {

    private Offer offer;

    public Seller(Offer offer) {
        this.offer = offer;
    }

    public float getOfferPrice() {
        return offer.getPrice();
    }
}

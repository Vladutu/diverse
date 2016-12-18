package ro.ucv.ace.auction;

/**
 * Created by Geo on 18.12.2016.
 */
public class Offer {

    private Product product;

    private float value;

    public Offer(Product product, float value) {
        this.product = product;
        this.value = value;
    }

    public float getPrice() {
        return value;
    }
}

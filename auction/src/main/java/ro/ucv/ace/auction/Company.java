package ro.ucv.ace.auction;

/**
 * Created by Geo on 21.11.2016.
 */
public class Company {

    private String name;

    private Advertisement advertisement;

    private BidingOptions bidingOptions;

    public Company() {
    }

    public Company(String name, Advertisement advertisement, BidingOptions bidingOptions) {
        this.name = name;
        this.advertisement = advertisement;
        this.bidingOptions = bidingOptions;

    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", advertisement=" + advertisement +
                ", bidingOptions=" + bidingOptions +
                '}';
    }

    public boolean canParticipate(int startingPrice) {
        return bidingOptions.canParticipate(startingPrice);
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public boolean canBid(int currentBid) {
        return bidingOptions.canBid(currentBid);
    }

    public int bid(int currentBid) {
        return bidingOptions.bid(currentBid);
    }

    public void substractFromBudget(int currentBid) {
        bidingOptions.substractFromBudget(currentBid);
    }
}

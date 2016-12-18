package ro.ucv.ace.auction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Geo on 18.12.2016.
 */
public class DoubleAuction {

    private List<Buyer> buyers;

    private List<Seller> sellers;

    public DoubleAuction(List<Buyer> buyers, List<Seller> sellers) {
        this.buyers = buyers;
        this.sellers = sellers;
    }

    public void start() {
        float mean = calculateMean();
        System.out.println("Mean = " + mean);
        removeBuyersWhoOfferLessThanMean(mean);
        removeSellersWhoOfferMoreThanMean(mean);
        sortBuyersAsc();
        sortSellersAsc();
        matchBuyersAndSellers();
    }

    private void matchBuyersAndSellers() {
        int length = Math.min(buyers.size(), sellers.size());

        for (int i = 0; i < length; i++) {
            Buyer buyer = buyers.get(i);
            Seller seller = sellers.get(i);
            System.out.println("Matched buyer who offer $" + buyer.getOfferPrice() + " with seller who offer $" +
                    seller.getOfferPrice() + ".");
        }
    }

    private void sortSellersAsc() {
        sellers = sellers.stream()
                .sorted((s1, s2) -> new Float(s1.getOfferPrice()).compareTo(s2.getOfferPrice()))
                .collect(Collectors.toList());
    }

    private void sortBuyersAsc() {
        buyers = buyers.stream()
                .sorted((b1, b2) -> new Float(b1.getOfferPrice()).compareTo(b2.getOfferPrice()))
                .collect(Collectors.toList());
    }

    private float calculateMean() {
        float mean = 0;
        for (Buyer buyer : buyers) {
            mean += buyer.getOfferPrice();
        }
        for (Seller seller : sellers) {
            mean += seller.getOfferPrice();
        }

        mean = mean / (buyers.size() + sellers.size());
        return mean;
    }

    private void removeSellersWhoOfferMoreThanMean(float mean) {
        sellers = sellers.stream()
                .filter(seller -> seller.getOfferPrice() < mean)
                .collect(Collectors.toList());
    }

    private void removeBuyersWhoOfferLessThanMean(final float mean) {
        buyers = buyers.stream()
                .filter(buyer -> buyer.getOfferPrice() > mean)
                .collect(Collectors.toList());
    }
}

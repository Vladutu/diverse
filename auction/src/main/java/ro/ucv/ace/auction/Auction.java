package ro.ucv.ace.auction;

import java.util.Collections;
import java.util.List;

/**
 * Created by Geo on 21.11.2016.
 */
public class Auction {

    private int currentBid;

    private List<Company> companies;

    private boolean hasBid;

    private Company lastBidder;

    public Auction(List<Company> participants, int startingPrice) {
        this.companies = participants;
        this.currentBid = startingPrice;
        this.hasBid = false;
    }


    public void start() {
        do {
            hasBid = false;
            companies.stream()
                    .filter(company -> company.canBid(currentBid))
                    .forEach(company -> {
                        currentBid += company.bid(currentBid);
                        hasBid = true;
                        lastBidder = company;
                    });
            Collections.shuffle(companies);
        }
        while (hasBid);
    }

    public Company getWinner() {
        lastBidder.substractFromBudget(currentBid);

        return lastBidder;
    }
}

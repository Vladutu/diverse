package ro.ucv.ace.auction;

import java.util.Random;

/**
 * Created by Geo on 21.11.2016.
 */
public class BidingOptions {

    private int budget;

    private int participationProbability;

    private int maxStep;

    private int limitPerAuction;

    private Random random = new Random();

    public BidingOptions() {
    }

    public BidingOptions(int budget, int participationProbability, int maxStep, int limitPerAuction) {
        this.budget = budget;
        this.participationProbability = participationProbability;
        this.maxStep = maxStep;
        this.limitPerAuction = limitPerAuction;
    }

    @Override
    public String toString() {
        return "BidingOptions{" +
                "budget=" + budget +
                ", participationProbability=" + participationProbability +
                ", maxStep=" + maxStep +
                ", limitPerAuction=" + limitPerAuction +
                '}';
    }

    public boolean canParticipate(int startingPrice) {
        int rand = random.nextInt() % 100 + 1;
        return rand <= participationProbability && budget >= startingPrice && limitPerAuction >= startingPrice;
    }

    public boolean canBid(int currentBid) {
        return currentBid < limitPerAuction && currentBid < budget;
    }

    public int bid(int currentBid) {
        int bid = random.nextInt() % Math.min(maxStep, limitPerAuction - currentBid) + 1;
        if (bid > budget) {
            bid = budget;
        }

        return bid;
    }

    public void substractFromBudget(int currentBid) {
        budget -= currentBid;
    }
}

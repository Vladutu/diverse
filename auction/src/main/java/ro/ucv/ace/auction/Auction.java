package ro.ucv.ace.auction;

import java.util.List;

/**
 * Created by Geo on 21.11.2016.
 */
public class Auction {

    private int currentBid;

    private List<Company> companies;

    public Auction(List<Company> participants, int startingPrice) {
        this.companies = participants;
        this.currentBid = startingPrice;
    }


    public Company begin() {
        companies.forEach(company->{
            if(company.canBid(currentBid)){
                currentBid += company.bid();
            }
        });

        return null;
    }
}

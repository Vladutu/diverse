package ro.ucv.ace.auction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Geo on 21.11.2016.
 */
public class WebsiteOwner {

    private List<Company> companies;

    private AdvertisementZone[] advertisementZones;

    private int startingPrice;

    public WebsiteOwner(List<Company> companies, int startingPrice) {
        this.companies = companies;
        advertisementZones = new AdvertisementZone[3];
        for (int i = 0; i < 3; i++) {
            advertisementZones[i] = new AdvertisementZone();
        }
        this.startingPrice = startingPrice;
    }

    public void beginAuction() {
        int index = 0;
        for (AdvertisementZone advertisementZone : advertisementZones) {
            List<Company> participants = companies.stream()
                    .filter(c -> c.canParticipate(startingPrice))
                    .collect(Collectors.toList());
            Auction auction = new Auction(participants, startingPrice);

            auction.start();
            Company winner = auction.getWinner();

            System.out.println(String.format("Winner for advertisement zone #%s is\n%s", index++, winner.toString()));
            advertisementZone.setAdvertisement(winner.getAdvertisement());
        }
    }
}

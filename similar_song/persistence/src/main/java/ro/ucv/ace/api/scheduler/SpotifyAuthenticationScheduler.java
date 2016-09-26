package ro.ucv.ace.api.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.ucv.ace.api.ISpotifyApi;

/**
 * Created by Geo on 26.09.2016.
 */
@Component
public class SpotifyAuthenticationScheduler {

    @Autowired
    private ISpotifyApi spotifyApi;

    @Scheduled(initialDelay = 0L, fixedRate = 1800000L)
    public void authenticate() {
        System.out.println("Authenticating to Spotify...");
        spotifyApi.authenticate();
        System.out.println("Authenticated!");
    }
}

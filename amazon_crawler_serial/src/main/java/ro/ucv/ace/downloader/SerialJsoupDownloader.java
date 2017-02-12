package ro.ucv.ace.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import ro.ucv.ace.exception.CaptchaException;
import ro.ucv.ace.exception.DownloadErrorException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Geo on 09.02.2017.
 */
@Component
public class SerialJsoupDownloader implements JsoupDownloader {

    private List<String> userAgents;

    private Random random = new Random();

    private long lastDownloadStart = 0;

    private static final int TIMEOUT = 60 * 1000;

    private static final int DELAY_MS = 2500;

    private static final int RANDOM_DELAY_MS = 300;


    public SerialJsoupDownloader() {
        userAgents = readUserAgents();
    }

    public Document download(String docUrl) {
        try {
            long downloadStart = System.currentTimeMillis();
            waitIfNeeded(downloadStart);
            lastDownloadStart = System.currentTimeMillis();

            Document document = Jsoup.connect(docUrl).timeout(TIMEOUT).userAgent(getRandomUserAgent()).get();
            if (document.html().contains("Sorry, we just need to make sure you're not a robot.")) {
                throw new CaptchaException("Captcha detected");
            }

            return document;
        } catch (IOException e) {
            throw new DownloadErrorException(e);
        }
    }

    private void waitIfNeeded(long downloadStart) {
        int actualDelay = DELAY_MS + random.nextInt() % RANDOM_DELAY_MS;
        long timeBetween = downloadStart - lastDownloadStart;

        if (timeBetween < actualDelay) {
            sleep(actualDelay - timeBetween);
        }
    }


    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<String> readUserAgents() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("user_agents.txt").getFile());
        List<String> userAgents = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                userAgents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAgents;
    }

    private String getRandomUserAgent() {
        int rand = random.nextInt() % userAgents.size();
        return userAgents.get(rand);
    }
}

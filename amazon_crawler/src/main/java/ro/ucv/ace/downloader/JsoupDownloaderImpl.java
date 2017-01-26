package ro.ucv.ace.downloader;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Geo on 25.01.2017.
 */
@Component
public class JsoupDownloaderImpl implements InitializingBean, DisposableBean, JsoupDownloader {

    private ExecutorService executorService;

    private List<String> userAgents;

    private int currentUserAgentIndex;

    public JsoupDownloaderImpl() {
        currentUserAgentIndex = 0;
        userAgents = readUserAgents();
        userAgents.forEach(System.out::println);
    }

    @Override
    public Future<Document> download(String url) {
        String userAgent = userAgents.get(currentUserAgentIndex);
        currentUserAgentIndex++;

        if (currentUserAgentIndex == userAgents.size()) {
            currentUserAgentIndex = 0;
        }

        return executorService.submit(new HtmlPageCallable(url, userAgent));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
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

}

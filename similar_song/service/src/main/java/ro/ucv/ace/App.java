package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.ServiceConfig;
import ro.ucv.ace.service.YoutubeService;

/**
 * Hello world!
 */
@Component
public class App {

    @Autowired
    private YoutubeService youtubeService;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext cxt = new AnnotationConfigApplicationContext(ServiceConfig.class);
        App app = (App) cxt.getBean(App.class);
        Thread.sleep(2000);

        String embedYoutubeUrl = app.youtubeService.getEmbedYoutubeUrl("Justin Bieber", "Baby");
        System.out.println(embedYoutubeUrl);
    }
}

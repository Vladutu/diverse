package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.SpringConfiguration;
import ro.ucv.ace.readability.Readability;
import ro.ucv.ace.readability.ReadabilityResult;
import ro.ucv.ace.statistics.ObjectFileWriter;
import ro.ucv.ace.statistics.ReplayStatistics;
import ro.ucv.ace.statistics.ReviewStatistics;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
@Component
public class App {

    @Autowired
    private ReviewStatistics reviewStatistics;

    @Autowired
    private ObjectFileWriter objectFileWriter;

    @Autowired
    private ReplayStatistics replayStatistics;

    @Autowired
    private Readability readability;

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        App app = ctx.getBean(App.class);
        ReadabilityResult readabilityResult = app.readability.computeReadability("Great Monitor! I was little hesitant at first because it's specs seemed a little lower than others, such as contrast ratio and response time. I'm an avid gamer who needed a new monitor right before college that wouldn't burn a hole in my pocket. Very happy with what I got. I also thought 21.5 was a little small for gaming, but it's pretty big in person and it'll definitely satisfy anyone who wants to watch movies or play games often. After about a months use, I've encountered no problem, and only see quality!");
        System.out.println(readabilityResult);

    }

    void reviewStatistics() {
        Map<String, List<Integer>> wordCounter = reviewStatistics.countWordsInReviewsGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\review_word_count_per_category.json", wordCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void replayStatistics() {
        Map<String, List<Integer>> wordCounter = replayStatistics.countWordsInReplaysGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\replay_word_count_per_category.json", wordCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

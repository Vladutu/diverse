package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.SpringConfiguration;
import ro.ucv.ace.readability.Readability;
import ro.ucv.ace.service.ProductService;
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

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        App app = ctx.getBean(App.class);
        app.readabilityReviewStatistics();
    }

    void readabilityReviewStatistics() {
        Map<String, Map<String, List<Double>>> map = reviewStatistics.computeLizabilityTestsGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\review_readability_per_category.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void readabilityReplayStatistics() {
        Map<String, Map<String, List<Double>>> map = replayStatistics.computeLizabilityTestsGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\replay_readability_per_category.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void wordReviewStatistics() {
        Map<String, List<Integer>> wordCounter = reviewStatistics.countWordsInReviewsGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\review_word_count_per_category.json", wordCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void wordReplayStatistics() {
        Map<String, List<Integer>> wordCounter = replayStatistics.countWordsInReplaysGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\replay_word_count_per_category.json", wordCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

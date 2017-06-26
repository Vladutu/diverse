package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.config.SpringConfiguration;
import ro.ucv.ace.interaction.ExportAuthorGraph;
import ro.ucv.ace.interaction.InteractionGraph;
import ro.ucv.ace.readability.Readability;
import ro.ucv.ace.sentiment_analysis.sentiwordnet.SentiWordNet;
import ro.ucv.ace.service.ProductService;
import ro.ucv.ace.statistics.*;

import java.io.IOException;
import java.util.HashMap;
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
    private ProductStatistics productStatistics;

    @Autowired
    private Readability readability;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthorStatistics authorStatistics;

    @Autowired
    private SentiWordNet sentiWordNet;

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        App app = ctx.getBean(App.class);
        app.testSentiWordNet();
    }


    void testSentiWordNet() {
        System.out.println("fgresgrdg#a " + sentiWordNet.extract("fgresgrdg", "a"));
        System.out.println("very#a " + sentiWordNet.extract("very", "a"));
        System.out.println("bad#a " + sentiWordNet.extract("bad", "a"));
        System.out.println("blue#a " + sentiWordNet.extract("blue", "a"));
        System.out.println("blue#n " + sentiWordNet.extract("blue", "n"));
    }

    void priceAndLength() {
        Map<String, List<Double>> map = productStatistics.averageReviewLengthAndProductPrice();
        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\price_and_length.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void helpfulVotesAndAIR() {
        Map<String, List<Double>> map = reviewStatistics.helpfulVotesAndAIR();
        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\helpful_votes_and_air.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void wordsAndHelpfulVotes() {
        Map<String, List<Integer>> map = reviewStatistics.countWordsAndUsefulness();
        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\words_and_helpful_votes.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void authorInteractionCategorized() {
        Map<String, InteractionGraph> interactionGraphMap = authorStatistics.createUndirectedCategorizedInteractionGraphs();
        Map<String, ExportAuthorGraph> resultMap = new HashMap<>();

        interactionGraphMap.forEach((key, value) -> {
            resultMap.put(key, value.createExportAuthorGraph());
        });

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\author_interaction_categorized.json", resultMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void authorInteraction() {
        InteractionGraph graph = authorStatistics.createUndirectedInteractionGraph();
        ExportAuthorGraph exGraph = graph.createExportAuthorGraph();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\author_interaction.json", exGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void productFeatures() {
        Map<String, List<Integer>> map = productStatistics.countFeaturesInProductsGroupedByCategory();

        try {
            objectFileWriter.writeObjectToFile("D:\\statistics\\product_features_count.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

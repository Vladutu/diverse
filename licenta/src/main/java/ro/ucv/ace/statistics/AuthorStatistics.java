package ro.ucv.ace.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Replay;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.interaction.Interaction;
import ro.ucv.ace.interaction.InteractionGraph;
import ro.ucv.ace.interaction.UndirectedInteractionGraph;
import ro.ucv.ace.repository.ReviewRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geo on 25.03.2017.
 */
@Component
@Transactional(readOnly = true)
public class AuthorStatistics {

    @Autowired
    private ReviewRepository reviewRepository;

    public InteractionGraph createUndirectedInteractionGraph() {
        InteractionGraph interactionGraph = new UndirectedInteractionGraph();

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            createReviewInteraction(interactionGraph, review);
        });


        return interactionGraph;
    }


    public Map<String, InteractionGraph> createUndirectedCategorizedInteractionGraphs() {
        Map<String, InteractionGraph> map = new HashMap<>();

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            String category = review.getProduct().getCategory().getName();
            map.computeIfAbsent(category, k -> new UndirectedInteractionGraph());

            InteractionGraph graph = map.get(category);
            createReviewInteraction(graph, review);

        });


        return map;
    }

    private void createReviewInteraction(InteractionGraph interactionGraph, Review review) {
        List<Replay> replays = review.getReplays();
        replays.forEach(replay -> {
            Interaction interaction = new Interaction(replay.getAuthor(), review.getAuthor());
            interactionGraph.addInteraction(interaction);

            createReplayInteraction(replay, interactionGraph);
        });
    }


    private void createReplayInteraction(Replay replay, InteractionGraph interactionGraph) {
        List<Replay> replays = replay.getReplays();

        replays.forEach(r -> {
            Interaction interaction = new Interaction(r.getAuthor(), replay.getAuthor());
            interactionGraph.addInteraction(interaction);
            createReplayInteraction(r, interactionGraph);
        });
    }
}

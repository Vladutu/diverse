package ro.ucv.ace.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Replay;
import ro.ucv.ace.interaction.Interaction;
import ro.ucv.ace.interaction.InteractionGraph;
import ro.ucv.ace.interaction.UndirectedInteractionGraph;
import ro.ucv.ace.repository.ReviewRepository;

import java.util.List;

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

            List<Replay> replays = review.getReplays();
            replays.forEach(replay -> {
                Interaction interaction = new Interaction(replay.getAuthor(), review.getAuthor());
                interactionGraph.addInteraction(interaction);

                createReplayInteraction(replay, interactionGraph);
            });


        });


        return interactionGraph;
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

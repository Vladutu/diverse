package ro.ucv.ace.interaction;

import ro.ucv.ace.entity.Author;

import java.util.*;

/**
 * Created by Geo on 25.03.2017.
 */
public class UndirectedInteractionGraph implements InteractionGraph {

    private Set<Author> authors = new HashSet<>();

    private List<Interaction> interactions = new ArrayList<>();

    @Override
    public void addInteraction(Interaction interaction) {
        Optional<Interaction> any = interactions.parallelStream()
                .filter(i -> isSameInteraction(interaction, i)).findAny();

        if (!any.isPresent()) {
            interactions.add(interaction);
            authors.add(interaction.getFrom());
            authors.add(interaction.getTo());
        } else {
            System.out.println("---------------------------------------Present-------------------------------------");
        }

    }

    @Override
    public ExportAuthorGraph createExportAuthorGraph() {
        ExportAuthorGraph graph = new ExportAuthorGraph();

        authors.forEach(author -> {
            AuthorNode node = new AuthorNode(author.getAmazonId(), author.getName());
            graph.addNode(node);
        });

        interactions.forEach(interaction -> {
            Author from = interaction.getFrom();
            Author to = interaction.getTo();
            InteractionEdge edge = new InteractionEdge(from.getAmazonId(), to.getAmazonId());
            graph.addEdge(edge);
        });

        return graph;
    }

    private boolean isSameInteraction(Interaction interaction, Interaction i) {
        return (i.getFrom().equals(interaction.getFrom()) && i.getTo().equals(interaction.getTo())) ||
                (i.getFrom().equals(interaction.getTo()) && i.getTo().equals(interaction.getFrom()));
    }
}

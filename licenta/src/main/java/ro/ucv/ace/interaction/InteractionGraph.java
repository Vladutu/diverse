package ro.ucv.ace.interaction;

/**
 * Created by Geo on 25.03.2017.
 */
public interface InteractionGraph {

    void addInteraction(Interaction interaction);

    ExportAuthorGraph createExportAuthorGraph();
}

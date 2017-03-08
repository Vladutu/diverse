package ro.ucv.ace.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 05.03.2017.
 */
public class SenticConcept {

    private Polarity polarity;

    private List<String> moodTags = new ArrayList<>();

    private List<String> semantics = new ArrayList<>();

    private Sentics sentics;

    public Polarity getPolarity() {
        return polarity;
    }

    public void setPolarity(Polarity polarity) {
        this.polarity = polarity;
    }

    public List<String> getMoodTags() {
        return moodTags;
    }

    public void setMoodTags(List<String> moodTags) {
        this.moodTags = moodTags;
    }

    public List<String> getSemantics() {
        return semantics;
    }

    public void setSemantics(List<String> semantics) {
        this.semantics = semantics;
    }

    public Sentics getSentics() {
        return sentics;
    }

    public void setSentics(Sentics sentics) {
        this.sentics = sentics;
    }

    @Override
    public String toString() {
        return "SenticConcept{" +
                "polarity=" + polarity +
                ", moodTags=" + moodTags +
                ", semantics=" + semantics +
                ", sentics=" + sentics +
                '}';
    }
}

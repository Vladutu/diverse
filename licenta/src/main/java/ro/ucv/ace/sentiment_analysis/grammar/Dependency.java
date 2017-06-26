package ro.ucv.ace.sentiment_analysis.grammar;

/**
 * Created by Geo on 26.06.2017.
 */
public class Dependency {

    private String relation;

    private Word governor;

    private Word dependent;

    public Dependency(String relation, Word governor, Word dependent) {
        this.relation = relation;
        this.governor = governor;
        this.dependent = dependent;
    }

    public String getRelation() {
        return relation;
    }

    public Word getGovernor() {
        return governor;
    }

    public Word getDependent() {
        return dependent;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "relation='" + relation + '\'' +
                ", governor=" + governor +
                ", dependent=" + dependent +
                '}';
    }
}

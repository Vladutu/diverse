package ro.ucv.ace.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency {

    private String relation;

    private Word governor;

    private Word dependent;

    private Double polarity;

    private boolean processable = true;

    public Dependency(String relation, Word governor, Word dependent) {
        this.relation = relation;
        this.governor = governor;
        this.dependent = dependent;
    }

    public boolean isProcessed() {
        return polarity != null;
    }

}

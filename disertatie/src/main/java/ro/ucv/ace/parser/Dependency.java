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

    private double polarity;

    public Dependency(String relation, Word governor, Word dependent) {
        this.relation = relation;
        this.governor = governor;
        this.dependent = dependent;
    }
}

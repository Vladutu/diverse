package ro.ucv.ace.parser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private int index;

    private String value;

    private String lemma;

    private String pos;

    private boolean negated;

    private double polarity;

    @ToString.Exclude
    @JsonIgnore
    private Word previous;

    @ToString.Exclude
    @JsonIgnore
    private Word next;

    public Word(int index, String value, String lemma, String pos) {
        this.index = index;
        this.value = value;
        this.lemma = lemma;
        this.pos = pos;
    }
}

package ro.ucv.ace.sentiment_analysis.grammar;

/**
 * Created by Geo on 26.06.2017.
 */
public class Word {

    private int index;

    private String value;

    private String lemma;

    private String pos;

    public Word(int index, String value, String lemma, String pos) {
        this.index = index;
        this.value = value;
        this.lemma = lemma;
        this.pos = pos;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public String getLemma() {
        return lemma;
    }

    public String getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "Word{" +
                "index=" + index +
                ", value='" + value + '\'' +
                ", lemma='" + lemma + '\'' +
                ", pos='" + pos + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word = (Word) o;

        if (index != word.index) {
            return false;
        }
        return value != null ? value.equals(word.value) : word.value == null;
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}

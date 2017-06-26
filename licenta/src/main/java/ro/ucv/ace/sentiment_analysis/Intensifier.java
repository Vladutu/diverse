package ro.ucv.ace.sentiment_analysis;

/**
 * Created by Geo on 26.06.2017.
 */
public class Intensifier {

    private String name;

    private int increase;

    public Intensifier(String name, int increase) {
        this.name = name;
        this.increase = increase;
    }

    public String getName() {
        return name;
    }

    public int getIncrease() {
        return increase;
    }
}

package ro.ucv.ace.sentiment;

import lombok.Getter;

@Getter
public class Intensifier {

    private String name;

    private int increase;

    public Intensifier(String name, int increase) {
        this.name = name;
        this.increase = increase;
    }
}

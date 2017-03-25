package ro.ucv.ace.interaction;

import ro.ucv.ace.entity.Author;

/**
 * Created by Geo on 25.03.2017.
 */
public class Interaction {

    private Author from;

    private Author to;

    public Interaction(Author from, Author to) {
        this.from = from;
        this.to = to;
    }

    public Author getFrom() {
        return from;
    }

    public Author getTo() {
        return to;
    }
}

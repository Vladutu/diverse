package ro.ucv.ace.auction;

/**
 * Created by Geo on 21.11.2016.
 */
public class Advertisement {

    private String content;

    public Advertisement() {
    }

    public Advertisement(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "content='" + content + '\'' +
                '}';
    }
}

package ro.ucv.ace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 08.12.2016.
 */
public class Node {

    private String text;

    private String path;

    private String extension;

    private boolean file;

    private List<Node> children = new ArrayList<>();

    public Node(String text, String path, boolean file, String extension) {
        this.text = text;
        this.path = path;
        this.file = file;
        this.extension = extension;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "Node{" +
                "text='" + text + '\'' +
                ", file=" + file +
                ", children=" + children +
                '}';
    }
}

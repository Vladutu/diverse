package ro.ucv.ace.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParseNode {
    private List<ParseNode> children = null;

    @Setter
    @Getter
    private String value;

    public boolean isLeaf() {
        return children == null;
    }

    public void addChild(ParseNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(child);
    }

    public int indexOfCommaAfterSubordinateClause() {
        if (!children.get(0).getValue().equalsIgnoreCase("SBAR") &&
                !children.get(0).getValue().equalsIgnoreCase("PP")) {
            return -1;
        }
        if (children.size() < 2) {
            return -1;
        }

        if (!children.get(1).getValue().equalsIgnoreCase(",")) {
            return -1;
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);

        children.get(0).executeForIndex(atomicInteger);

        return atomicInteger.get() + 1;
    }

    private void executeForIndex(AtomicInteger atomicInteger) {
        if (isLeaf()) {
            atomicInteger.incrementAndGet();
        }
        if (children == null) {
            return;
        }

        children.forEach(child -> child.executeForIndex(atomicInteger));
    }
}

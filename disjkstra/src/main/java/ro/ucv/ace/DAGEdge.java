package ro.ucv.ace;

/**
 * Created by Geo on 05.11.2016.
 */
public class DAGEdge {

    private int source;

    private int destination;

    private int cost;

    public DAGEdge(int source, int destination, int cost) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DAGEdge dagEdge = (DAGEdge) o;

        if (source != dagEdge.source) {
            return false;
        }
        return destination == dagEdge.destination;

    }

    @Override
    public int hashCode() {
        int result = source;
        result = 31 * result + destination;
        return result;
    }
}

package ro.ucv.ace.generator;

/**
 * Created by Geo on 05.11.2016.
 */
public class DCGEdge {

    private int source;

    private int destination;

    private int cost;

    public DCGEdge(int source, int destination, int cost) {
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

        DCGEdge DCGEdge = (DCGEdge) o;

        if (source != DCGEdge.source) {
            return false;
        }
        return destination == DCGEdge.destination;

    }

    @Override
    public int hashCode() {
        int result = source;
        result = 31 * result + destination;
        return result;
    }

    @Override
    public String toString() {
        return "DCGEdge{" +
                "source=" + source +
                ", destination=" + destination +
                ", cost=" + cost +
                '}';
    }
}

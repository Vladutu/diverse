package ro.ucv.ace.graph;

/**
 * Created by Geo on 05.11.2016.
 */
public class Vertex implements Comparable<Vertex> {

    private int id;

    private Double distanceToSource;

    public Vertex(int id) {
        this.id = id;
        this.distanceToSource = 0.0;
    }

    public Double getDistanceToSource() {
        return distanceToSource;
    }

    public void setDistanceToSource(Double distanceToSource) {
        this.distanceToSource = distanceToSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vertex vertex = (Vertex) o;

        return id == vertex.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Vertex(" + id + ")";
    }

    @Override
    public int compareTo(Vertex o) {
        return distanceToSource.compareTo(o.distanceToSource);
    }
}

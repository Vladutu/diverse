package ro.ucv.ace.graph.model;

/**
 * Created by Geo on 12.11.2016.
 */
public class Vertex {

    private int id;

    private Double distanceToSource;

    public Vertex(int i) {
        id = i;
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
}

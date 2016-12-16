package ro.ucv.ace.dijkstra.mpi;

import ro.ucv.ace.graph.model.Vertex;

import java.io.Serializable;

/**
 * Created by Geo on 14.12.2016.
 */
public class RelaxationResult implements Serializable {

    private boolean update;

    private double value;

    private Vertex vertex;

    public RelaxationResult(boolean update, double value, Vertex vertex) {
        this.update = update;
        this.value = value;
        this.vertex = vertex;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public String toString() {
        return "RelaxationResult{" +
                "update=" + update +
                ", value=" + value +
                ", vertex=" + vertex +
                '}';
    }
}

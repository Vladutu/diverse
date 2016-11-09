package ro.ucv.ace.dijkstra.parallelv2;

/**
 * Created by Geo on 09.11.2016.
 */
public class Condition {

    private volatile boolean flag;

    public Condition(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

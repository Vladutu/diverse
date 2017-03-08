package ro.ucv.ace.model;

/**
 * Created by Geo on 05.03.2017.
 */
public class Sentics {

    private double sensitivity;

    private double attention;

    private double pleasantness;

    private double aptitude;

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getAttention() {
        return attention;
    }

    public void setAttention(double attention) {
        this.attention = attention;
    }

    public double getPleasantness() {
        return pleasantness;
    }

    public void setPleasantness(double pleasantness) {
        this.pleasantness = pleasantness;
    }

    public double getAptitude() {
        return aptitude;
    }

    public void setAptitude(double aptitude) {
        this.aptitude = aptitude;
    }

    @Override
    public String toString() {
        return "Sentics{" +
                "sensitivity=" + sensitivity +
                ", attention=" + attention +
                ", pleasantness=" + pleasantness +
                ", aptitude=" + aptitude +
                '}';
    }
}

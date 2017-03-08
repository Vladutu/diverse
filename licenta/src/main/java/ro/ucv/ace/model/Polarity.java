package ro.ucv.ace.model;

/**
 * Created by Geo on 05.03.2017.
 */
public class Polarity {

    private String value;

    private double intensity;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "Polarity{" +
                "value='" + value + '\'' +
                ", intensity=" + intensity +
                '}';
    }
}

package ro.ucv.ace.readability;

/**
 * Created by Geo on 19.03.2017.
 */
public class ReadabilityResult {

    private final double gunningFogIndex;

    private final double fleschKincaidGradeLevel;

    private final double fleschReadingEase;

    private final double automatedReadabilityIndex;

    private final double colemanLiauIndex;

    public ReadabilityResult(double gunningFogIndex, double fleschKincaidGradeLevel, double fleschReadingEase, double automatedReadabilityIndex, double colemanLiauIndex) {

        this.gunningFogIndex = gunningFogIndex;
        this.fleschKincaidGradeLevel = fleschKincaidGradeLevel;
        this.fleschReadingEase = fleschReadingEase;
        this.automatedReadabilityIndex = automatedReadabilityIndex;
        this.colemanLiauIndex = colemanLiauIndex;
    }

    public double getGunningFogIndex() {
        return gunningFogIndex;
    }

    public double getFleschKincaidGradeLevel() {
        return fleschKincaidGradeLevel;
    }

    public double getFleschReadingEase() {
        return fleschReadingEase;
    }

    public double getAutomatedReadabilityIndex() {
        return automatedReadabilityIndex;
    }

    public double getColemanLiauIndex() {
        return colemanLiauIndex;
    }
}

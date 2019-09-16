package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;

public class SentimentUtils {

    public static boolean neg(double polarity) {
        return polarity < 0;
    }

    public static boolean pos(double polarity) {
        return polarity >= 0;
    }

    public static void setPolarity(Dependency dependency, double polarity) {
        dependency.setPolarity(polarity);
        dependency.getGovernor().setPolarity(polarity);
        dependency.getDependent().setPolarity(polarity);
    }
}

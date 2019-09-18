package ro.ucv.ace.sentiment;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Word;

public class SentimentUtils {

    public static boolean neg(double polarity) {
        return polarity < 0;
    }

    public static boolean pos(double polarity) {
        return polarity >= 0;
    }

    public static void setPolarity(Dependency dependency, double polarity) {
        Word dependent = dependency.getDependent();
        Word governor = dependency.getGovernor();

        dependency.setPolarity(polarity);
        if (governor.getPolarity() == 0) {
            governor.setPolarity(polarity);
        }
        if (dependent.getPolarity() == 0) {
            dependent.setPolarity(polarity);
        }
    }
}

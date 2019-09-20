package ro.ucv.ace.sentiment;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Word;

public class SentimentUtils {

    public static boolean neg(double polarity) {
        return polarity < 0;
    }

    public static boolean pos(double polarity) {
        return polarity > 0;
    }

    public static void setPolarity(Dependency dependency, double polarity) {
        Word dependent = dependency.getDependent();
        Word governor = dependency.getGovernor();

        dependency.setPolarity(polarity);
        governor.setPolarity(polarity);
        dependent.setPolarity(polarity);
    }
}

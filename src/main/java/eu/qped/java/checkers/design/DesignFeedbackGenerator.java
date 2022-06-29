package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.data.DesignCheckEntry;
import eu.qped.java.checkers.design.data.DesignCheckMessage;
import eu.qped.java.checkers.design.data.DesignCheckMessageMulti;
import eu.qped.java.checkers.design.data.DesignCheckMessageSingle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;

/**
 * Represents helper class to generate design feedback suggestions as strings.
 *
 * @author Jannik Seus
 */
public class DesignFeedbackGenerator {

    /**
     * Generates a suggestion for the student depending on the exceeding of a metric's already calculated value.
     *
     * @param metric     the given metric
     * @param lowerBound the lower threshold of the metric not to be exceeded
     * @param upperBound the upper threshold of the metric not to be exceeded
     * @return a nicely formatted suggestion as String.
     */
    public static String generateSuggestion(Metric metric, boolean lowerBound, boolean upperBound) {
        if (!lowerBound && !upperBound) {
            return "You are within the " + metric.toString() + "'s threshold.";
        } else if (lowerBound && !upperBound) {
            return "The " + metric.toString() + "'s value is too low: " + generateMetricSpecificSuggestion(metric, true);
        } else if (!lowerBound) {
            return "The " + metric.toString() + "'s value is too high: " + generateMetricSpecificSuggestion(metric, false);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Generates a suggestion for the student depending on the exceeding of lower or upper bound
     * of already calculated value of the given metric.
     *
     * @param metric        the given metric
     * @param exceededLower true if lower bound was exceeded, false if upper bound was exceeded
     * @return a metric and boundary specific suggestion
     */
    public static String generateMetricSpecificSuggestion(Metric metric, boolean exceededLower) {
        if (exceededLower) {
            return generateMetricSpecificSuggestionLower(metric);
        } else {
            return generateMetricSpecificSuggestionUpper(metric);
        }
    }

    /**
     * Generates a suggestion for the student depending on the exceeding of the upper bound
     * of already calculated value of the given metric.
     *
     * @param metric the given metric
     * @return a metric and lower bound specific suggestion
     */
    private static String generateMetricSpecificSuggestionUpper(Metric metric) {
        switch (metric) {
            case AMC:
                return Suggestion.AMC.getUpperBoundReachedSuggestion();
            case CA:
                return Suggestion.CA.getUpperBoundReachedSuggestion();
            case CAM:
                return Suggestion.CAM.getUpperBoundReachedSuggestion();
            case CBM:
                return Suggestion.CBM.getUpperBoundReachedSuggestion();
            case CBO:
                return Suggestion.CBO.getUpperBoundReachedSuggestion();
            case CC:
                return Suggestion.CC.getUpperBoundReachedSuggestion();
            case CE:
                return Suggestion.CE.getUpperBoundReachedSuggestion();
            case DAM:
                return Suggestion.DAM.getUpperBoundReachedSuggestion();
            case DIT:
                return Suggestion.DIT.getUpperBoundReachedSuggestion();
            case IC:
                return Suggestion.IC.getUpperBoundReachedSuggestion();
            case LCOM:
                return Suggestion.LCOM.getUpperBoundReachedSuggestion();
            case LCOM3:
                return Suggestion.LCOM3.getUpperBoundReachedSuggestion();
            case LOC:
                return Suggestion.LOC.getUpperBoundReachedSuggestion();
            case MFA:
                return Suggestion.MFA.getUpperBoundReachedSuggestion();
            case MOA:
                return Suggestion.MOA.getUpperBoundReachedSuggestion();
            case NOC:
                return Suggestion.NOC.getUpperBoundReachedSuggestion();
            case NPM:
                return Suggestion.NPM.getUpperBoundReachedSuggestion();
            case RFC:
                return Suggestion.RFC.getUpperBoundReachedSuggestion();
            case WMC:
                return Suggestion.WMC.getUpperBoundReachedSuggestion();
        }
        throw new IllegalArgumentException("Illegal metric type/ illegal suggestion.");
    }

    /**
     * Generates a suggestion for the student depending on the exceeding of the lower bound
     * of already calculated value of the given metric.
     *
     * @param metric the given metric
     * @return a metric and lower bound specific suggestion
     */
    private static String generateMetricSpecificSuggestionLower(Metric metric) {
        switch (metric) {

            case AMC:
                return Suggestion.AMC.getLowerBoundReachedSuggestion();
            case CA:
                return Suggestion.CA.getLowerBoundReachedSuggestion();
            case CAM:
                return Suggestion.CAM.getLowerBoundReachedSuggestion();
            case CBM:
                return Suggestion.CBM.getLowerBoundReachedSuggestion();
            case CBO:
                return Suggestion.CBO.getLowerBoundReachedSuggestion();
            case CC:
                return Suggestion.CC.getLowerBoundReachedSuggestion();
            case CE:
                return Suggestion.CE.getLowerBoundReachedSuggestion();
            case DAM:
                return Suggestion.DAM.getLowerBoundReachedSuggestion();
            case DIT:
                return Suggestion.DIT.getLowerBoundReachedSuggestion();
            case IC:
                return Suggestion.IC.getLowerBoundReachedSuggestion();
            case LCOM:
                return Suggestion.LCOM.getLowerBoundReachedSuggestion();
            case LCOM3:
                return Suggestion.LCOM3.getLowerBoundReachedSuggestion();
            case LOC:
                return Suggestion.LOC.getLowerBoundReachedSuggestion();
            case MFA:
                return Suggestion.MFA.getLowerBoundReachedSuggestion();
            case MOA:
                return Suggestion.MOA.getLowerBoundReachedSuggestion();
            case NOC:
                return Suggestion.NOC.getLowerBoundReachedSuggestion();
            case NPM:
                return Suggestion.NPM.getLowerBoundReachedSuggestion();
            case RFC:
                return Suggestion.RFC.getLowerBoundReachedSuggestion();
            case WMC:
                return Suggestion.WMC.getLowerBoundReachedSuggestion();
        }
        throw new IllegalArgumentException("Illegal metric type/ illegal suggestion.");
    }

    /**
     * Generates the DesignFeedback to the corresponding classes, metrics, and designSettings (min/max thresholds)
     *
     * @param metricsMap     the map containing classnames, metrics and corresponding values
     * @param designSettings the settings on which the feedback depends on //TODO wip configure design settings
     * @return the generated Feedback as a List.
     */
    public static List<DesignFeedback> generateDesignFeedbacks(List<DesignCheckEntry> metricsMap, DesignSettings designSettings) {
        List<DesignFeedback> feedbacks = new ArrayList<>();

        metricsMap.forEach(designCheckEntry -> {

            String className = designCheckEntry.getClassName();
            List<DesignCheckMessage> metricsForClass = designCheckEntry.getMetricsForClass();

            if (metricsForClass != null) {
                metricsForClass.forEach(metricForClass -> {
                    boolean lowerThresholdReached;
                    boolean upperThresholdReached;
                    Metric metric = metricForClass.getMetric();
                    String suggestion;
                    double metricValue;
                    Map<String, Integer> metricValues;

                    if (metricForClass instanceof DesignCheckMessageSingle) {
                        metricValue = ((DesignCheckMessageSingle) metricForClass).getMetricValue();

                        lowerThresholdReached = isThresholdReached(metric, designSettings, metricValue, true);
                        upperThresholdReached = isThresholdReached(metric, designSettings, metricValue, false);
                        suggestion = generateSuggestion(metric, lowerThresholdReached, upperThresholdReached);
                        boolean addFeedback = (lowerThresholdReached || upperThresholdReached);
                        if (addFeedback) {
                            feedbacks.add(new DesignFeedback(className, metric.getDescription(), metric, metricValue, lowerThresholdReached, upperThresholdReached, suggestion));
                        }

                    } else {
                        metricValues = ((DesignCheckMessageMulti) metricForClass).getMetricValues();
                        for (Map.Entry<String, Integer> entry : metricValues.entrySet()) {
                            suggestion = "For method " + entry.getKey() + ":\t";
                            lowerThresholdReached = isThresholdReached(metric, designSettings, entry.getValue(), true);
                            upperThresholdReached = isThresholdReached(metric, designSettings, entry.getValue(), false);
                            suggestion += generateSuggestion(metric, lowerThresholdReached, upperThresholdReached) + "\n";
                            boolean addFeedback = (lowerThresholdReached || upperThresholdReached);
                            if (addFeedback) {
                                feedbacks.add(new DesignFeedback(className, metric.getDescription(), metric, (double) entry.getValue(), lowerThresholdReached, upperThresholdReached, suggestion));
                            }
                        }
                    }
                });
            }
        });
        return feedbacks;
    }

    /**
     * Checks whether the lower or upper threshold of a given metricThreshold was exceeded.
     *
     * @param metric         the given metric
     * @param designSettings the settings for design guidelines
     * @param value          the given metricThreshold's value
     * @param lower          determines whether to check the lower or upper threshold
     * @return whether the minimum (lower=true) or maximum (lower=false) threshold was exceeded.
     */
    private static boolean isThresholdReached(Metric metric, DesignSettings designSettings, double value, boolean lower) {

        switch (metric) {
            case AMC:
                return lower ? value < designSettings.getAmc().getLowerBound() : value > designSettings.getAmc().getUpperBound();
            case CAM:
                return lower ? value < designSettings.getCam().getLowerBound() : value > designSettings.getCam().getUpperBound();
            case CA:
                return lower ? value < designSettings.getCa().getLowerBound() : value > designSettings.getCa().getUpperBound();
            case CBM:
                return lower ? value < designSettings.getCbm().getLowerBound() : value > designSettings.getCbm().getUpperBound();
            case CBO:
                return lower ? value < designSettings.getCbo().getLowerBound() : value > designSettings.getCbo().getUpperBound();
            case CC:
                return lower ? value < designSettings.getCc().getLowerBound() : value > designSettings.getCc().getUpperBound();
            case CE:
                return lower ? value < designSettings.getCe().getLowerBound() : value > designSettings.getCe().getUpperBound();
            case DAM:
                return lower ? value < designSettings.getDam().getLowerBound() : value > designSettings.getDam().getUpperBound();
            case DIT:
                return lower ? value < designSettings.getDit().getLowerBound() : value > designSettings.getDit().getUpperBound();
            case IC:
                return lower ? value < designSettings.getIc().getLowerBound() : value > designSettings.getIc().getUpperBound();
            case LCOM:
                return lower ? value < designSettings.getLcom().getLowerBound() : value > designSettings.getLcom().getUpperBound();
            case LCOM3:
                return lower ? value < designSettings.getLcom3().getLowerBound() : value > designSettings.getLcom3().getUpperBound();
            case LOC:
                return lower ? value < designSettings.getLoc().getLowerBound() : value > designSettings.getLoc().getUpperBound();
            case MOA:
                return lower ? value < designSettings.getMoa().getLowerBound() : value > designSettings.getMoa().getUpperBound();
            case MFA:
                return lower ? value < designSettings.getMfa().getLowerBound() : value > designSettings.getMfa().getUpperBound();
            case NOC:
                return lower ? value < designSettings.getNoc().getLowerBound() : value > designSettings.getNoc().getUpperBound();
            case NPM:
                return lower ? value < designSettings.getNpm().getLowerBound() : value > designSettings.getNpm().getUpperBound();
            case RFC:
                return lower ? value < designSettings.getRfc().getLowerBound() : value > designSettings.getRfc().getUpperBound();
            case WMC:
                return lower ? value < designSettings.getWmc().getLowerBound() : value > designSettings.getWmc().getUpperBound();
            default:
                throw new IllegalArgumentException("Illegal Metric given.");
        }
    }

    private enum Suggestion {
        AMC("Increase your average method size, e.g. by joining multiple methods with mostly the same functionalities from over-engineering.",
                "Decrease your average method size, e.g. by delegating functionalities to other newly created methods."),
        CA("This class is used by too few other classes. Is this class even necessary? Can you implement this class's functionalities into already existing classes?",
                "This class is used by too many other classes. Can you outsource some functionalities into already existing or new classes?"),
        CAM("This class and their methods are or are close to being un-cohesive. Assimilate methods in your class in order to increase readability and decrease complexity at once.",
                "This class and their methods are too cohesive. Your implemented methods are too close to being the same methods."),
        CBM("The methods in this class are not or are hardly coupled, which means they have (close to) no interdependencies. Is this rational your your class?",
                "The methods in this class are coupled to high, which means too many interdependencies, coordination and information flow between them. Try to minimize these dependencies between your methods."),
        CBO("The sum of all class couplings in this class is (close to) zero, which means they have (close to) no interdependencies to other classes. Is this rational your your class? Also, refer to afferent/efferent coupling metric.",
                "The sum of all class couplings in this class is too high, which means too many interdependencies, coordination and information flow between them. Try to minimize these dependencies from this class to other classes. Also, refer to afferent/efferent coupling metric."),
        CC("This method in the given class has very few different paths to take. It would be allowed to increase its complexity.",
                "This method in the given class is too complex, too many paths are taken (ite-statements). Try to decrease the complexity by delegating functionalities into other methods or classes."),
        CE("This class is using too few other classes. Can some functionalities be implemented into other classes and be used?",
                "This class is using too many other classes. Can some functionalities be joined by other classes or even be implemented in this specific class?"),
        DAM("This class contains very few private (protected) attributes compared to to the total number of attributes. Try to encapsulate your class (make fields private, only access them by methods contained in this specific class if possible).",
                "This class contains many private (protected) attributes compared to to the total number of attributes. Encapsulation is important, but sometimes over-engineering. Is this rational?"),
        DIT("This class has very few superclasses or only one superclass (Object.java). Is inheritance a valid option? ",
                "This class has many superclasses. Is this much inheritance possible over-engineering? Do certain subclasses have too similar or too few functionalities?"),
        IC("This class is coupled to few or no parent classes. Overriding parent methods could be a suitable option here.",
                "This class is coupled to many parent classes. Overriding parent methods makes sense, but is not always necessary."),
        LCOM("", ""),
        LCOM3("", ""),
        LOC("", ""),
        MFA("", ""),
        MOA("", ""),
        NOC("", ""),
        NPM("", ""),
        RFC("", ""),
        WMC("", "");

        private final String lowerBoundReachedSuggestion;
        private final String upperBoundReachedSuggestion;

        Suggestion(String lowerBoundReachedSuggestion, String upperBoundReachedSuggestion) {
            this.lowerBoundReachedSuggestion = lowerBoundReachedSuggestion;
            this.upperBoundReachedSuggestion = upperBoundReachedSuggestion;
        }

        public String getLowerBoundReachedSuggestion() {
            return lowerBoundReachedSuggestion;
        }

        public String getUpperBoundReachedSuggestion() {
            return upperBoundReachedSuggestion;
        }
    }
}

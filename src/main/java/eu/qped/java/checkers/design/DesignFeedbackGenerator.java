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
     * Generates a suggestion for the student depending on the exceeding of am already calculated value of a metric.
     *
     * @param metric     the given metric
     * @param lowerBound the lower threshold of the metric not to be exceeded
     * @param upperBound the upper threshold of the metric not to be exceeded
     * @return a nicely formatted suggestion as String.
     */
    public static String generateDefaultSuggestions(Metric metric, boolean lowerBound, boolean upperBound) {
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

        if (metric != null) {
            switch (metric) {
                case AMC:
                    return DefaultDesignSuggestion.AMC.getUpperBoundReachedSuggestion();
                case CA:
                    return DefaultDesignSuggestion.CA.getUpperBoundReachedSuggestion();
                case CAM:
                    return DefaultDesignSuggestion.CAM.getUpperBoundReachedSuggestion();
                case CBM:
                    return DefaultDesignSuggestion.CBM.getUpperBoundReachedSuggestion();
                case CBO:
                    return DefaultDesignSuggestion.CBO.getUpperBoundReachedSuggestion();
                case CC:
                    return DefaultDesignSuggestion.CC.getUpperBoundReachedSuggestion();
                case CE:
                    return DefaultDesignSuggestion.CE.getUpperBoundReachedSuggestion();
                case DAM:
                    return DefaultDesignSuggestion.DAM.getUpperBoundReachedSuggestion();
                case DIT:
                    return DefaultDesignSuggestion.DIT.getUpperBoundReachedSuggestion();
                case IC:
                    return DefaultDesignSuggestion.IC.getUpperBoundReachedSuggestion();
                case LCOM:
                    return DefaultDesignSuggestion.LCOM.getUpperBoundReachedSuggestion();
                case LCOM3:
                    return DefaultDesignSuggestion.LCOM3.getUpperBoundReachedSuggestion();
                case LOC:
                    return DefaultDesignSuggestion.LOC.getUpperBoundReachedSuggestion();
                case MFA:
                    return DefaultDesignSuggestion.MFA.getUpperBoundReachedSuggestion();
                case MOA:
                    return DefaultDesignSuggestion.MOA.getUpperBoundReachedSuggestion();
                case NOC:
                    return DefaultDesignSuggestion.NOC.getUpperBoundReachedSuggestion();
                case NPM:
                    return DefaultDesignSuggestion.NPM.getUpperBoundReachedSuggestion();
                case RFC:
                    return DefaultDesignSuggestion.RFC.getUpperBoundReachedSuggestion();
                case WMC:
                    return DefaultDesignSuggestion.WMC.getUpperBoundReachedSuggestion();
            }
        }
        throw new IllegalArgumentException("Invalid metric given.");
    }

    /**
     * Generates a suggestion for the student depending on the exceeding of the lower bound
     * of already calculated value of the given metric.
     *
     * @param metric the given metric
     * @return a metric and lower bound specific suggestion
     */
    private static String generateMetricSpecificSuggestionLower(Metric metric) {
        if (metric != null) {
            switch (metric) {
                case AMC:
                    return DefaultDesignSuggestion.AMC.getLowerBoundReachedSuggestion();
                case CA:
                    return DefaultDesignSuggestion.CA.getLowerBoundReachedSuggestion();
                case CAM:
                    return DefaultDesignSuggestion.CAM.getLowerBoundReachedSuggestion();
                case CBM:
                    return DefaultDesignSuggestion.CBM.getLowerBoundReachedSuggestion();
                case CBO:
                    return DefaultDesignSuggestion.CBO.getLowerBoundReachedSuggestion();
                case CC:
                    return DefaultDesignSuggestion.CC.getLowerBoundReachedSuggestion();
                case CE:
                    return DefaultDesignSuggestion.CE.getLowerBoundReachedSuggestion();
                case DAM:
                    return DefaultDesignSuggestion.DAM.getLowerBoundReachedSuggestion();
                case DIT:
                    return DefaultDesignSuggestion.DIT.getLowerBoundReachedSuggestion();
                case IC:
                    return DefaultDesignSuggestion.IC.getLowerBoundReachedSuggestion();
                case LCOM:
                    return DefaultDesignSuggestion.LCOM.getLowerBoundReachedSuggestion();
                case LCOM3:
                    return DefaultDesignSuggestion.LCOM3.getLowerBoundReachedSuggestion();
                case LOC:
                    return DefaultDesignSuggestion.LOC.getLowerBoundReachedSuggestion();
                case MFA:
                    return DefaultDesignSuggestion.MFA.getLowerBoundReachedSuggestion();
                case MOA:
                    return DefaultDesignSuggestion.MOA.getLowerBoundReachedSuggestion();
                case NOC:
                    return DefaultDesignSuggestion.NOC.getLowerBoundReachedSuggestion();
                case NPM:
                    return DefaultDesignSuggestion.NPM.getLowerBoundReachedSuggestion();
                case RFC:
                    return DefaultDesignSuggestion.RFC.getLowerBoundReachedSuggestion();
                case WMC:
                    return DefaultDesignSuggestion.WMC.getLowerBoundReachedSuggestion();
            }
        }
        throw new IllegalArgumentException("Invalid metric given.");
    }

    /**
     * Generates the DesignFeedback to the corresponding classes, metrics, and designSettings (min/max thresholds)
     *
     * @param metricsMap     the map containing classnames, metrics and corresponding values
     * @param designSettings the settings on which the feedback depends on //TODO wip configure design settings
     * @return the generated Feedback as a List.
     */
    public static List<DesignFeedback> generateDesignFeedbacks(List<DesignCheckEntry> metricsMap, DesignSettings
            designSettings) {
        List<DesignFeedback> feedbacks = new ArrayList<>();

        metricsMap.forEach(designCheckEntry -> {

            String className = designCheckEntry.getClassName();
            List<DesignCheckMessage> metricsForClass = designCheckEntry.getMetricsForClass();

            if (metricsForClass != null) {
                metricsForClass.forEach(metricForClass -> {
                    boolean lowerThresholdReached;
                    boolean upperThresholdReached;
                    Metric metric = metricForClass.getMetric();
                    String suggestion = "";
                    double metricValue;
                    Map<String, Integer> metricValues;

                    if (metricForClass instanceof DesignCheckMessageSingle) {
                        metricValue = ((DesignCheckMessageSingle) metricForClass).getMetricValue();

                        lowerThresholdReached = isThresholdReached(metric, designSettings, metricValue, true);
                        upperThresholdReached = isThresholdReached(metric, designSettings, metricValue, false);
                        DesignSuggestion customSuggestion = designSettings.getCustomSuggestions().get(metric);

                        if (lowerThresholdReached) {
                            if (customSuggestion == null || customSuggestion.getSuggestionLowerBoundExceeded() == null || customSuggestion.getSuggestionLowerBoundExceeded().isBlank()) {
                                suggestion = generateMetricSpecificSuggestionLower(metric);
                            } else {
                                suggestion = customSuggestion.getSuggestionLowerBoundExceeded();
                            }
                        } else if (upperThresholdReached) {
                            if (customSuggestion == null || customSuggestion.getSuggestionUpperBoundExceeded() == null || customSuggestion.getSuggestionUpperBoundExceeded().isBlank()) {
                                suggestion = generateMetricSpecificSuggestionUpper(metric);
                            } else {
                                suggestion = customSuggestion.getSuggestionUpperBoundExceeded();
                            }
                        }

                        boolean addFeedback = (lowerThresholdReached || upperThresholdReached);
                        if (addFeedback) {
                            feedbacks.add(new DesignFeedback(className, metric.getDescription(), metric, metricValue, suggestion));
                        }


                    } else {
                        metricValues = ((DesignCheckMessageMulti) metricForClass).getMetricValues();
                        for (Map.Entry<String, Integer> entry : metricValues.entrySet()) {
                            suggestion = "For method " + entry.getKey() + ":\t";
                            lowerThresholdReached = isThresholdReached(metric, designSettings, entry.getValue(), true);
                            upperThresholdReached = isThresholdReached(metric, designSettings, entry.getValue(), false);

                            DesignSuggestion customSuggestion = designSettings.getCustomSuggestions().get(metric);
                            if (customSuggestion == null || customSuggestion.getSuggestionLowerBoundExceeded() == null || customSuggestion.getSuggestionLowerBoundExceeded().isBlank()) {
                                suggestion += generateDefaultSuggestions(metric, lowerThresholdReached, upperThresholdReached);
                            } else {
                                suggestion += customSuggestion;
                            }

                            boolean addFeedback = (lowerThresholdReached || upperThresholdReached);
                            if (addFeedback) {
                                feedbacks.add(new DesignFeedback(className, metric.getDescription(), metric, (double) entry.getValue(), suggestion));
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
        if (metric != null) {
            switch (metric) {
                case AMC:
                    return lower ? value < designSettings.getAmcThreshold().getLowerBound() : value > designSettings.getAmcThreshold().getUpperBound();
                case CAM:
                    return lower ? value < designSettings.getCamThreshold().getLowerBound() : value > designSettings.getCamThreshold().getUpperBound();
                case CA:
                    return lower ? value < designSettings.getCaThreshold().getLowerBound() : value > designSettings.getCaThreshold().getUpperBound();
                case CBM:
                    return lower ? value < designSettings.getCbmThreshold().getLowerBound() : value > designSettings.getCbmThreshold().getUpperBound();
                case CBO:
                    return lower ? value < designSettings.getCboThreshold().getLowerBound() : value > designSettings.getCboThreshold().getUpperBound();
                case CC:
                    return lower ? value < designSettings.getCcThreshold().getLowerBound() : value > designSettings.getCcThreshold().getUpperBound();
                case CE:
                    return lower ? value < designSettings.getCeThreshold().getLowerBound() : value > designSettings.getCeThreshold().getUpperBound();
                case DAM:
                    return lower ? value < designSettings.getDamThreshold().getLowerBound() : value > designSettings.getDamThreshold().getUpperBound();
                case DIT:
                    return lower ? value < designSettings.getDitThreshold().getLowerBound() : value > designSettings.getDitThreshold().getUpperBound();
                case IC:
                    return lower ? value < designSettings.getIcThreshold().getLowerBound() : value > designSettings.getIcThreshold().getUpperBound();
                case LCOM:
                    return lower ? value < designSettings.getLcomThreshold().getLowerBound() : value > designSettings.getLcomThreshold().getUpperBound();
                case LCOM3:
                    return lower ? value < designSettings.getLcom3Threshold().getLowerBound() : value > designSettings.getLcom3Threshold().getUpperBound();
                case LOC:
                    return lower ? value < designSettings.getLocThreshold().getLowerBound() : value > designSettings.getLocThreshold().getUpperBound();
                case MOA:
                    return lower ? value < designSettings.getMoaThreshold().getLowerBound() : value > designSettings.getMoaThreshold().getUpperBound();
                case MFA:
                    return lower ? value < designSettings.getMfaThreshold().getLowerBound() : value > designSettings.getMfaThreshold().getUpperBound();
                case NOC:
                    return lower ? value < designSettings.getNocThreshold().getLowerBound() : value > designSettings.getNocThreshold().getUpperBound();
                case NPM:
                    return lower ? value < designSettings.getNpmThreshold().getLowerBound() : value > designSettings.getNpmThreshold().getUpperBound();
                case RFC:
                    return lower ? value < designSettings.getRfcThreshold().getLowerBound() : value > designSettings.getRfcThreshold().getUpperBound();
                case WMC:
                    return lower ? value < designSettings.getWmcThreshold().getLowerBound() : value > designSettings.getWmcThreshold().getUpperBound();
            }
        }
        throw new IllegalArgumentException("Illegal Metric given.");
    }

    /**
     * Enumeration that contains suggestions for every metric when the lower or upper boundary was exceeded.
     *
     * @author Jannik Seus
     */
    public enum DefaultDesignSuggestion {
        AMC("Increase your average method size, e.g. by joining multiple methods with mostly the same functionalities from over-engineering.",
                "Decrease your average method size, e.g. by delegating functionalities to other newly created methods."),
        CA("This class is used by too few other classes. Is this class even necessary? Can you implement this class's functionalities into already existing classes?",
                "This class is used by too many other classes. Can you outsource some functionalities into already existing or new classes?"),
        CAM("This class and their methods are or are close to being un-cohesive. Assimilate methods in your class in order to increase readability and decrease complexity at once.",
                "This class and their methods are too cohesive. Your implemented methods are too close to being the same methods."),
        CBM("The methods in this class are not or are hardly coupled, which means they have (close to) no interdependencies. Is this reasonable for your class?",
                "The methods in this class are coupled to high, which means too many interdependencies, coordination and information flow between them. Try to minimize these dependencies between your methods."),
        CBO("The sum of all class couplings in this class is (close to) zero, which means they have (close to) no interdependencies to other classes. Is this reasonable for your class? Also, refer to afferent/efferent coupling metric.",
                "The sum of all class couplings in this class is too high, which means too many interdependencies, coordination and information flow between them. Try to minimize these dependencies from this class to other classes. Also, refer to afferent/efferent coupling metric."),
        CC("This method in the given class has very few different paths to take. It would be allowed to increase its complexity.",
                "This method in the given class is too complex, too many paths are taken (ite-statements). Try to decrease the complexity by delegating functionalities into other methods or classes."),
        CE("This class is using too few other classes. Can some functionalities be implemented into other classes and be used?",
                "This class is using too many other classes. Can some functionalities be joined by other classes or even be implemented in this specific class?"),
        DAM("This class contains very few private (protected) attributes compared to to the total number of attributes. Try to encapsulate your class (make fields private, only access them by methods contained in this specific class if possible).",
                "This class contains many private (protected) attributes compared to to the total number of attributes. Encapsulation is important, but sometimes over-engineering. Is this reasonable?"),
        DIT("This class has very few superclasses or only one superclass (Object.java). Is inheritance a valid option? ",
                "This class has many superclasses. Is this much inheritance possible over-engineering? Do certain subclasses have too similar or too few functionalities?"),
        IC("This class is coupled to few or no parent classes. Overriding parent methods could be a suitable option here.",
                "This class is coupled to many parent classes. Overriding parent methods makes sense, but is not always necessary."),
        LCOM("The modularisation of this class is too low. Too many methods operate on different attributes.",
                "The modularisation of this class is quite high. You could think about the necessity if your class is very small."),
        LCOM3("The modularisation of this class is too low. Too many methods operate on different attributes.",
                "The modularisation of this class is quite high. You could think about the necessity if your class is very small."),
        LOC("This class contains very few lines of code. Is it even necessary to put these functionalities into a separate class?",
                "This class contains too many lines of code, it could be considered as a \"God Class\". Try to keep only the main functionality in this class, others should be implemented into other (new) classes."),
        MFA("The functional abstraction of this class ist quite low. If possible, try to let his class inherit some methods.",
                "The functional abstraction of this class is very high. Consider refactoring this class into an abstract class if this is not yet the case."),
        MOA("This class contains too few class fields. In order to increase class aggregation, also increase the number of fields or merge this class into another.",
                "This class contains too many class fields. Try to inline fields or extract functionalities into other classes."),
        NOC("This class has very few or no immediate descendants. Would extending this class be reasonable?",
                "This class has too much immediate descendants. Consider using multiple inheritance, i.e. creating subclasses of a subclass."),
        NPM("This class uses few or no public methods. Is this intended?",
                "This class uses mostly public methods. Try to decrease their visibility to force the information hiding principle."),
        RFC("This class has too few or zero (in-)directly executable methods. Is this class even necessary then?",
                "This class is able to (in-)directly execute too many methods. This is a typical smell for a god class. Does your class have one main functionality? Can some functionalities be extracted into other existing or new classes?"),
        WMC("This class contains too few or zero methods. Is this class even necessary then?",
                "This class contains too many methods. This is a typical smell for a god class. Does your class have one main functionality? Can some functionalities be extracted into other existing or new classes?");

        private final String lowerBoundReachedSuggestion;
        private final String upperBoundReachedSuggestion;

        DefaultDesignSuggestion(String lowerBoundReachedSuggestion, String upperBoundReachedSuggestion) {
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

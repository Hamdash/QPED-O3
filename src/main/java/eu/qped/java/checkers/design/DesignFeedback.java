package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.data.DesignCheckEntry;
import eu.qped.java.checkers.design.data.DesignCheckMessage;
import eu.qped.java.checkers.design.data.DesignCheckMessageMulti;
import eu.qped.java.checkers.design.data.DesignCheckMessageSingle;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;

/**
 * Data class representing the feedback for design presented to the user.
 *
 * @author Jannik Seus
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DesignFeedback extends Feedback {

    private String className;
    private Metric metric;
    private Double value;
    private boolean lowerThresholdReached;
    private boolean upperThresholdReached;
    private String suggestion;
    //private String example; //TODO schwierig, sich hier bei Klassendesign kurz zu halten

    @Builder
    public DesignFeedback(String className, String body, Metric metric, Double value, boolean lowerThresholdReached, boolean upperThresholdReached, String suggestion) {
        super(body);
        this.className = className;
        this.metric = metric;
        this.value = value;
        this.lowerThresholdReached = lowerThresholdReached;
        this.upperThresholdReached = upperThresholdReached;
        this.suggestion = suggestion;
    }

    /**
     * Generates a suggestion for the student depending on the exceeding of a metric's already calculated value.
     *
     * @param metric         the given metric
     * @param lowerThreshold the metric's lower threshold not to be exceeded
     * @param upperThreshold the metric's upper threshold not to be exceeded
     * @return a nicely formatted suggestion as String.
     */
    public static String generateSuggestionSingle(Metric metric, boolean lowerThreshold, boolean upperThreshold) {
        if (!lowerThreshold && !upperThreshold) {
            return "You are within the " + metric.toString() + "'s threshold.";
        } else if (lowerThreshold && !upperThreshold) {
            return "The " + metric.toString() + "'s value is too low.";
        } else if (upperThreshold && !lowerThreshold) {
            return "The " + metric.toString() + "'s value is too high.";
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Generates a suggestion for the student depending on the exceeding of a metric's already calculated value.
     *
     * @param metric         the given metric
     * @param lowerThreshold the metric's lower threshold not to be exceeded
     * @param upperThreshold the metric's upper threshold not to be exceeded
     * @return a nicely formatted suggestion as String.
     */
    public static String generateSuggestionMulti(Metric metric, boolean lowerThreshold, boolean upperThreshold) {
        if (!lowerThreshold && !upperThreshold) {
            return "You are within the " + metric.toString() + "'s threshold.";
        } else if (lowerThreshold && !upperThreshold) {
            return "The " + metric.toString() + "'s value is too low.";
        } else if (upperThreshold && !lowerThreshold) {
            return "The " + metric.toString() + "'s value is too high.";
        } else {
            throw new IllegalArgumentException();
        }
    }
    //TODO methods: generateMetricSuggestionLowerThExceeded(..); and generateMetricSuggestionUpperThExceeded(..);

    /**
     * Generates the DesignFeedback to the corresponding classes, metrics, and designSettings (min/max thresholds)
     *
     * @param metricsMap     the map containing classnames, metrics and corresponding values
     * @param designSettings the settings on which the feedback depends on //TODO wip
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
                        suggestion = generateSuggestionSingle(metric, lowerThresholdReached, upperThresholdReached);
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
                            suggestion += generateSuggestionSingle(metric, lowerThresholdReached, upperThresholdReached) + "\n";
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
     * @return whether the min(lower=true) or max(lower=false) threshold was exceeded.
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

    @Override
    public String toString() {
        StringBuilder feedbackString = new StringBuilder();
        if (lowerThresholdReached || upperThresholdReached) {
            if (lowerThresholdReached) {
                feedbackString.append("Lower ");
            } else {
                feedbackString.append("Upper ");
            }
            feedbackString
                    .append("threshold of metric '")
                    .append(this.metric.toString())
                    .append("' in class '").append(this.className)
                    .append("' exceeded.\t")
                    .append("Thresholds: ").append("(")
                    .append(this.metric.getDefaultLowerBound())
                    .append(", ")
                    .append(this.metric.getDefaultUpperBound())
                    .append(")");
        } else {
            feedbackString
                    .append("threshold of Metric '")
                    .append(this.metric.toString())
                    .append("' in class '").append(this.className)
                    .append("' not exceeded\t");
        }
        feedbackString
                .append("Value=").append(this.value)
                .append(",\t suggestion: ").append(this.metric.getDescription());
        return feedbackString.toString();

    }
}

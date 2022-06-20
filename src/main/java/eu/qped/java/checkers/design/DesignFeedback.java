package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.data.DesignCheckEntry;
import eu.qped.java.checkers.design.data.DesignCheckMessage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jannik Seus
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DesignFeedback extends Feedback {
    private String className;
    private SaveMapResults.Metric metric;
    private Double value;
    private boolean lowerThresholdReached;
    private boolean upperThresholdReached;
    private String suggestion;
    //private String example; //TODO schwierig, sich hier bei Klassendesign kurz zu halten

    @Builder
    public DesignFeedback(String className, String body, SaveMapResults.Metric metric, Double value, boolean lowerThresholdReached, boolean upperThresholdReached, String suggestion) {
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
     * @param metric the given metric
     * @param lowerThreshold the metric's lower threshold not to be exceeded
     * @param upperThreshold the metric's upper threshold not to be exceeded
     * @return a nicely formatted suggestion as String.
     */
    public static String generateSuggestion(SaveMapResults.Metric metric, boolean lowerThreshold, boolean upperThreshold) {
        if (!lowerThreshold && !upperThreshold) {
            return "You are within the " + metric.toString() + "'s threshold.";
        } else if (lowerThreshold) {
            return "The " + metric.toString() + "'s value is too low.";
        } else if (upperThreshold) {
            return "The " + metric.toString() + "'s value is too high.";
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Generates the DesignFeedback to the corresponding classes, metrics, and designSettings (min/max thresholds)
     *
     * @param metricsMap the map containing classnames, metrics and corresponding values
     * @param designSettings the settings on which the feedback depends on //TODO wip
     * @return the generated Feedback as a List.
     */
    public static List<DesignFeedback> generateDesignFeedback(List<DesignCheckEntry> metricsMap, DesignSettings designSettings) {
        List<DesignFeedback> feedbacks = new ArrayList<>();

        metricsMap.forEach(designCheckEntry -> {

            String className = designCheckEntry.getClassName();
            List<DesignCheckMessage> metricsForClass = designCheckEntry.getMetricsForClass();

            if (metricsForClass != null) {
                metricsForClass.forEach(metricForClass -> {
                    SaveMapResults.Metric metric = metricForClass.getMetric();
                    double metricValue = metricForClass.getMetricValue();

                    boolean[] isThresholdReached = isThresholdReached(metric, designSettings, metricValue);
                    boolean lowerThreshold = isThresholdReached[0];
                    boolean upperThreshold = isThresholdReached[1];
                    String suggestion = generateSuggestion(metric, lowerThreshold, upperThreshold);

                    feedbacks.add(new DesignFeedback(className, metric.getDescription(), metric, metricValue, lowerThreshold, upperThreshold, suggestion));
                });
            }
        });
        return feedbacks;
    }

    /**
     * Checks whether the lower or upper threshold of a given metric was exceeded.
     *
     * @param metric the given metric
     * @param designSettings
     * @param value the given metric's value
     * @return an array of size 2 which contains whether the lower [0] and upper [1] threshold was reached-
     */
    private static boolean[] isThresholdReached(SaveMapResults.Metric metric, DesignSettings designSettings, double value) {
        boolean lower = false;
        boolean upper = false;

        switch (metric) {
            case AMC:
                if(value > designSettings.getAmc().getMaxThreshold()) upper = true;
                if(value < designSettings.getAmc().getMinThreshold()) lower = true;
                break;
            case CAM:
                if(value > designSettings.getCam().getMaxThreshold()) upper = true;
                if(value < designSettings.getCam().getMinThreshold()) lower = true;
                break;
            case CA:
                if(value > designSettings.getCa().getMaxThreshold()) upper = true;
                if(value < designSettings.getCa().getMinThreshold()) lower = true;
                break;
            case CBM:
                if(value > designSettings.getCbm().getMaxThreshold()) upper = true;
                if(value < designSettings.getCbm().getMinThreshold()) lower = true;
                break;
            case CBO:
                if(value > designSettings.getCbo().getMaxThreshold()) upper = true;
                if(value < designSettings.getCbo().getMinThreshold()) lower = true;
                break;
            case CC:
                if(value > designSettings.getCc().getMaxThreshold()) upper = true;
                if(value < designSettings.getCc().getMinThreshold()) lower = true;
                break;
            case CE:
                if(value > designSettings.getCe().getMaxThreshold()) upper = true;
                if(value < designSettings.getCe().getMinThreshold()) lower = true;
                break;
            case CIS:
                if(value > designSettings.getCis().getMaxThreshold()) upper = true;
                if(value < designSettings.getCis().getMinThreshold()) lower = true;
                break;
            case DAM:
                if(value > designSettings.getDam().getMaxThreshold()) upper = true;
                if(value < designSettings.getDam().getMinThreshold()) lower = true;
                break;
            case DIT:
                if(value > designSettings.getDit().getMaxThreshold()) upper = true;
                if(value < designSettings.getDit().getMinThreshold()) lower = true;
                break;
            case IC:
                if(value > designSettings.getIc().getMaxThreshold()) upper = true;
                if(value < designSettings.getIc().getMinThreshold()) lower = true;
                break;
            case LCOM:
                if(value > designSettings.getLcom().getMaxThreshold()) upper = true;
                if(value < designSettings.getLcom().getMinThreshold()) lower = true;
                break;
            case LCOM3:
                if(value > designSettings.getLcom3().getMaxThreshold()) upper = true;
                if(value < designSettings.getLcom3().getMinThreshold()) lower = true;
                break;
            case LOC:
                if(value > designSettings.getLoc().getMaxThreshold()) upper = true;
                if(value < designSettings.getLoc().getMinThreshold()) lower = true;
                break;
            case MOA:
                if(value > designSettings.getMoa().getMaxThreshold()) upper = true;
                if(value < designSettings.getMoa().getMinThreshold()) lower = true;
                break;
            case MFA:
                if(value > designSettings.getMfa().getMaxThreshold()) upper = true;
                if(value < designSettings.getMfa().getMinThreshold()) lower = true;
                break;
            case NOC:
                if(value > designSettings.getNoc().getMaxThreshold()) upper = true;
                if(value < designSettings.getNoc().getMinThreshold()) lower = true;
                break;
            case NPM:
                if(value > designSettings.getNpm().getMaxThreshold()) upper = true;
                if(value < designSettings.getNpm().getMinThreshold()) lower = true;
                break;
            case RFC:
                if(value > designSettings.getRfc().getMaxThreshold()) upper = true;
                if(value < designSettings.getRfc().getMinThreshold()) lower = true;
                break;
            case WMC:
                if(value > designSettings.getWmc().getMaxThreshold()) upper = true;
                if(value < designSettings.getWmc().getMinThreshold()) lower = true;
                break;
        }
        return new boolean[]{lower, upper};
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
                    .append(this.metric.getDefaultThresholdMin())
                    .append(", ")
                    .append(this.metric.getDefaultThresholdMax())
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

package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    private String suggestion; //TODO is this even necessary?

    private Map<String, Map<SaveMapResults.Metric, Double>> metricsMap;


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

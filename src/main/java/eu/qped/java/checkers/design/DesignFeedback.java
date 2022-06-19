package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    public static void main(String[] args) {
        System.out.print(DesignFeedback.builder()
                        .className("TestClass.java")
                        .metric(SaveMapResults.Metric.CBM)
                        .value(2.54d)
                        .body(SaveMapResults.Metric.CBM.getDescription())
                        .lowerThresholdReached(false)
                        .upperThresholdReached(true)
                        .suggestion("lower your coupling")
                .build().toString());
    }
}

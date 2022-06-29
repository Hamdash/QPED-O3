package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    private boolean lowerBoundReached;
    private boolean upperBoundReached;
    private String suggestion;
    //private String example; //TODO schwierig, sich hier bei Klassendesign kurz zu halten

    @Builder
    public DesignFeedback(String className, String body, Metric metric, Double value, boolean lowerBoundReached, boolean upperBoundReached, String suggestion) {
        super(body);
        this.className = className;
        this.metric = metric;
        this.value = value;
        this.lowerBoundReached = lowerBoundReached;
        this.upperBoundReached = upperBoundReached;
        this.suggestion = suggestion;
    }

    //TODO methods: generateMetricSuggestionLowerThExceeded(..); and generateMetricSuggestionUpperThExceeded(..);

    @Override
    public String toString() {
        StringBuilder feedbackString = new StringBuilder();
        if (lowerBoundReached || upperBoundReached) {
            if (lowerBoundReached) {
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

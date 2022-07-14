package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;

/**
 * Data class representing the feedback for design presented to the user.
 *
 * @author Jannik Seus
 */
@Getter
@Setter
public class DesignFeedback extends Feedback {

    private String className;
    private Metric metric;
    private Double value;
    private String suggestion;

    @Builder
    public DesignFeedback(String className, String body, Metric metric, Double value, String suggestion) {
        super(body);
        this.className = className;
        this.metric = metric;
        this.value = value;
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("In class '")
                .append(getClassName())
                .append(".java'")
                .append("\n")
                .append(getMetric())
                .append(" (")
                .append(super.getBody())
                .append(")")
                .append("\n")
                .append("Measured at: ")
                .append(getValue())
                .append("\n")
                .append(suggestion)
                .toString();
    }
}

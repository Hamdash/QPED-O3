package eu.qped.java.checkers.metrics.data;

import lombok.Getter;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.*;

/**
 * Subclass for metric messages when only a single value single calculated for one class
 * (others than e.g. Cyclomatic Complexity where for each method in a given class a value is calculated).
 *
 * @author Jannik Seus
 */
@Getter
public class MetricsCheckerMessageSingle extends MetricsCheckerMessage {

    private final double metricValue;

    /**
     * Main constructor.
     *
     * @param metric      the given metric
     * @param metricValue the calculated value of the given metric for a class
     */
    public MetricsCheckerMessageSingle(Metric metric, double metricValue) {
        super(metric);
        this.metricValue = metricValue;
    }
}

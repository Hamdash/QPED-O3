package eu.qped.java.checkers.metrics.data.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric;

/**
 * Abstract class for metric messages.
 * For concrete Implementations see:
 * - {@link MetricsCheckerMessageSingle} and
 * - {@link MetricsCheckerMessageMulti} and
 *
 * @author Jannik Seus
 */
@Getter
@AllArgsConstructor
public abstract class MetricsCheckerMessage implements Comparable<MetricsCheckerMessage>{

    private Metric metric;

    @Override
    public int compareTo(MetricsCheckerMessage otherMetricsCheckerMessage) {
        return this.metric.toString().compareTo(otherMetricsCheckerMessage.getMetric().toString());
    }
}

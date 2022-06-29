package eu.qped.java.checkers.design.configuration;

import eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.Metric;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a class to define a {@link #lowerBound} and an {@link #upperBound} to a specific {@link #metric}.
 * The boundaries are meant to be inclusive.
 *
 * @author Jannik Seus
 */
@Data
@Builder
public class MetricThreshold implements Comparable<MetricThreshold>{

    private Metric metric;
    private double lowerBound;
    private double upperBound;

    public MetricThreshold(Metric metric) {
        this.metric = metric;
        if (this.metric == null) {
            throw new IllegalStateException("A metric must be specified.");
        }
        this.lowerBound = this.metric.getDefaultLowerBound();
        this.upperBound = this.metric.getDefaultUpperBound();
    }

    public MetricThreshold(Metric metric, double lowerBound, double upperBound) {
        this.metric = metric;
        if (this.metric == null) {
            throw new IllegalStateException("A metric must be specified.");
        }
        if (lowerBound <= upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        } else {
            this.lowerBound = upperBound;
            this.upperBound = lowerBound;
        }
    }

    @Override
    public int compareTo(MetricThreshold o) {
        return this.metric.toString().compareTo(o.getMetric().toString());
    }
}

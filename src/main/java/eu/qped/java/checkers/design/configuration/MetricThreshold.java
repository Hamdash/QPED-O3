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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class MetricThreshold implements Comparable<MetricThreshold>{

    private Metric metric;
    private double lowerBound;
    private double upperBound;

    public MetricThreshold(double lowerBound, double upperBound) {
        if (lowerBound <= upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        } else {
            this.lowerBound = upperBound;
            this.upperBound = lowerBound;
        }
    }

    public void setDefaultLowerBound() {
        this.lowerBound = metric.getDefaultLowerBound();
    }

    public void setDefaultUpperBound() {
        this.upperBound = metric.getDefaultUpperBound();
    }

    @Override
    public int compareTo(MetricThreshold o) {
        return this.metric.toString().compareTo(o.getMetric().toString());
    }
}

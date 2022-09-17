package eu.qped.java.checkers.metrics.settings;

import eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a class to define a {@link #min} and an {@link #max} to a specific {@link #metric}.
 * The boundaries are meant to be inclusive.
 *
 * @author Jannik Seus
 */
@Getter
@Setter
public class MetricThreshold implements Comparable<MetricThreshold> {

    private Metric metric;
    private double min;
    private double max;
    private boolean noMax;

    /**
     * Constructor that sets a lower or upper threshold to a specific value.
     *
     * @param metric the given metric
     * @param max the value of the threshold
     * @param min determines whether bound represents the lower or upper threshold
     * @param noMax determines whether this specific metric does not need an upper threshold
     */
    public MetricThreshold(Metric metric, double min, double max, boolean noMax) {
        this.metric = metric;
        if (this.metric == null) {
            throw new IllegalStateException("A metric must be specified.");
        }
        if (min == -1d || max == -1d) {
            throw new IllegalStateException("Illegal bound specified");
        }

        if (min <= max) {
            this.min = min;
            if (noMax && max < 0) {
                this.max = Double.MAX_VALUE;
            } else {
                this.max = max;
            }
        } else {
            this.min = max;
            if (noMax && (min < 0)) {
                this.max = Double.MAX_VALUE;
            } else {
                this.max = min;
            }
        }
    }

    @Override
    public int compareTo(MetricThreshold o) {
        if (o == null) return 1;
        return this.metric.toString().compareTo(o.getMetric().toString());
    }
}

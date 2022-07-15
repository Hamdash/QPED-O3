package eu.qped.java.checkers.metrics.data.report;

import eu.qped.java.checkers.metrics.MetricsChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Class represents an Entry used in {@link MetricsChecker} for storing
 * a classname and a list of {@link MetricsCheckerMessage}s where this {@link #className}'s class metrics are stored.
 *
 * @author Jannik Seus
 */
@Getter
@AllArgsConstructor
public class MetricCheckerEntry implements Comparable<MetricCheckerEntry> {

    private String className;
    private List<MetricsCheckerMessage> metricsForClass;

    /**
     * Compares DesignCheckEntry depending on alphabetically by {@link #className}.
     *
     * @param otherMetricCheckerEntry DesignCheckEntry to which this DesignCheckEntry is compared to.
     */
    @Override
    public int compareTo(MetricCheckerEntry otherMetricCheckerEntry) {
        return this.className.compareTo(otherMetricCheckerEntry.getClassName());
    }
}

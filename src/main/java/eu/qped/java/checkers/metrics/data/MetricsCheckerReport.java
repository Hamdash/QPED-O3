package eu.qped.java.checkers.metrics.data;

import eu.qped.java.checkers.metrics.MetricsChecker;
import lombok.*;

import java.util.List;

/**
 * Represents a report for the {@link MetricsChecker}.
 *
 * @author Jannik Seus
 */

@AllArgsConstructor
@Builder
@Getter
@Setter
public class MetricsCheckerReport {

    private List<String> pathsToClassFiles;
    private List<MetricCheckerEntry> metricsMap;

}

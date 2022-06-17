package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.syntax.CompiledSourceType;
import eu.qped.java.checkers.syntax.SyntaxError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a report for the {@link DesignChecker}.
 *
 * @author Jannik Seus
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignCheckReport {

    /**
     * the map where the calculated metrics are stored in.
     * (k1: classname, v1: (k2: metric, v2: value) )
     */
    private Map<String, Map<SaveMapResults.Metric, Double>> metricsMap;
    private String codeAsString;
    private String path;
    private DesignConfigurator designConfigurator;

    public String toString() {
        StringBuilder reportString  = new StringBuilder("{ ");
        metricsMap.forEach((className, value) -> {
            reportString
                    .append("\n\t")
                    .append(className)
                    .append(": { ");

            value.forEach((key, value1) -> {
                String classMetric = key.toString();
                String metricValue = value1.toString();
                reportString
                        .append(classMetric)
                        .append(": ")
                        .append(metricValue)
                        .append(", ");
            });
            reportString
                    .deleteCharAt(reportString.lastIndexOf(","))
                    .append("}");
        });
        return reportString.append("\n}").toString();
    }
}

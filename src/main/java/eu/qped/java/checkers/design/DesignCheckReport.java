package eu.qped.java.checkers.design;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.style.reportModel.ReportFileEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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

    @JsonProperty("files")
    private List<String> files;

    private Map<String, Map<SaveMapResults.Metric, Double>> metricsMap;
}

package eu.qped.java.checkers.design.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.qped.java.checkers.design.DesignChecker;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.design.data.DesignCheckEntry;
import lombok.*;

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
@Getter
@Setter
public class DesignCheckReport {

    @JsonProperty("files")
    private List<String> files;

    //private Map<String, Map<SaveMapResults.Metric, Double>> metricsMap;

    private List<DesignCheckEntry> metricsMap;


    public static void main(String[] args) {
        DesignCheckReport designCheckReport = DesignCheckReport.builder()
                .metricsMap(List.of(
                        new DesignCheckEntry("Class A", List.of(
                                new DesignCheckMessage(SaveMapResults.Metric.AMC, 204),
                                new DesignCheckMessage(SaveMapResults.Metric.RFC, 4.5),
                                new DesignCheckMessage(SaveMapResults.Metric.WMC, -1)

                        )),
                        new DesignCheckEntry("Class B", List.of(
                                new DesignCheckMessage(SaveMapResults.Metric.DAM, 204),
                                new DesignCheckMessage(SaveMapResults.Metric.MFA, 4.5),
                                new DesignCheckMessage(SaveMapResults.Metric.NOC, -1)

                        )),
                        new DesignCheckEntry("Class C", List.of(
                                new DesignCheckMessage(SaveMapResults.Metric.CA, 204),
                                new DesignCheckMessage(SaveMapResults.Metric.DIT, 4.5),
                                new DesignCheckMessage(SaveMapResults.Metric.MOA, -1)

                        ))))
                .build();
    }
}

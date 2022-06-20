package eu.qped.java.checkers.design.configuration;

import eu.qped.java.checkers.design.ckjm.SaveMapResults.Metric;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Jannik Seus
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class MetricThreshold {

    private Metric metric;
    private double minThreshold;
    private double maxThreshold;

    public void setDefaultThresholdMin() {
        this.minThreshold = metric.getDefaultThresholdMin();
    }

    public void setDefaultThresholdMax() {
        this.maxThreshold = metric.getDefaultThresholdMax();
    }


}

package eu.qped.java.checkers.design.data;

import eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jannik Seus
 */
@Data
@AllArgsConstructor
public class DesignCheckMessageSingle extends DesignCheckMessage {

    private double metricValue;

    public DesignCheckMessageSingle(DesignCheckEntryHandler.Metric metric, double metricValue) {
        super(metric);
        this.metricValue = metricValue;
    }
}

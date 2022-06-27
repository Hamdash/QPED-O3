package eu.qped.java.checkers.design.data;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;

/**
 * Subclass for metric messages when multiple values are calculated for one class,
 * e.g. Cyclomatic Complexity for each method in a given class.
 *
 * @author Jannik Seus
 */
@Data
@AllArgsConstructor
public class DesignCheckMessageMulti extends DesignCheckMessage {

    private Map<String, Integer> metricValues;

    public DesignCheckMessageMulti(Metric metric, Map<String, Integer> metricValues) {
        super(metric);
        this.metricValues = metricValues;
    }
}

package eu.qped.java.checkers.design.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;

/**
 * Abstract class for metric messages.
 * For concrete Implementations see:
 * - {@link DesignCheckMessageSingle} and
 * - {@link DesignCheckMessageMulti} and
 *
 * @author Jannik Seus
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class DesignCheckMessage implements Comparable<DesignCheckMessage>{

    private Metric metric;

    /**
     * Default constructor
     */
    public DesignCheckMessage() {}

    @Override
    public int compareTo(DesignCheckMessage otherDesignCheckMessage) {
        return this.metric.toString().compareTo(otherDesignCheckMessage.getMetric().toString());
    }
}

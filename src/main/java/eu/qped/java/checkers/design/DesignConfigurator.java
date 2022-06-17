package eu.qped.java.checkers.design;

import eu.qped.java.checkers.mass.QFDesignSettings;
import lombok.*;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

import static eu.qped.java.checkers.design.ckjm.SaveMapResults.*;
import static eu.qped.java.checkers.design.ckjm.SaveMapResults.Metric.*;

/**
 * Class representing a configurator for the {@link DesignChecker}'s design guidelines.
 *
 * @author Jannik Seus
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DesignConfigurator {

    //@Builder.Default If used, IDE keeps getting stuck on parsing when compiling
    private Map<Metric, Double> metricsThresholds = Map.ofEntries(
            Map.entry(WMC, WMC.getInitialValue()),
            Map.entry(DIT, DIT.getInitialValue()),
            Map.entry(NOC, NOC.getInitialValue()),
            Map.entry(CBO, CBO.getInitialValue()),
            Map.entry(RFC, RFC.getInitialValue()),
            Map.entry(LCOM, LCOM.getInitialValue()),
            Map.entry(CA, CA.getInitialValue()),
            Map.entry(CE, CE.getInitialValue()),
            Map.entry(NPM, NPM.getInitialValue()),
            Map.entry(CIS, CIS.getInitialValue()),
            Map.entry(LCOM3, LCOM3.getInitialValue()),
            Map.entry(LOC, LOC.getInitialValue()),
            Map.entry(DAM, DAM.getInitialValue()),
            Map.entry(MOA, MOA.getInitialValue()),
            Map.entry(MFA, MFA.getInitialValue()),
            Map.entry(CAM, CAM.getInitialValue()),
            Map.entry(IC, IC.getInitialValue()),
            Map.entry(CBM, CBM.getInitialValue()),
            Map.entry(AMC, AMC.getInitialValue()),
            Map.entry(CC, CC.getInitialValue())
    );

    public QFDesignSettings generateDesignSettings() {
        QFDesignSettings designSettings = new QFDesignSettings();
        try {
            designSettings.setAmc(0,99);
        } catch (IllegalArgumentException | NullPointerException e) {
            LogManager.getLogger(getClass()).warn(e.getMessage());
        }
        return designSettings;    }

    /**
     * Method configuring a specific key-value-pair in the {@link #metricsThresholds}.
     *
     * @param metric the metric whose value is to be set (key)
     * @param value the given metric's value who is to bet set (value)
     */
    public void configure(Metric metric, double value) {
        this.metricsThresholds.put(metric, value);
    }
}

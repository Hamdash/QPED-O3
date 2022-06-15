package eu.qped.java.checkers.design;

import lombok.*;

import java.util.Map;

import static eu.qped.java.checkers.design.Metric.*;

/**
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
            Map.entry(WMC, 0d),
            Map.entry(DIT, 0d),
            Map.entry(NOC, 0d),
            Map.entry(CBO, 0d),
            Map.entry(RFC, 0d),
            Map.entry(LCOM, 0d),
            Map.entry(CA, 0d),
            Map.entry(CE, 0d),
            Map.entry(NPM, 0d),
            Map.entry(CIS, 0d),
            Map.entry(LCOM3, 0d),
            Map.entry(LOC, 0d),
            Map.entry(DAM, 0d),
            Map.entry(MOA, 0d),
            Map.entry(MFA, 0d),
            Map.entry(CAM, 0d),
            Map.entry(IC, 0d),
            Map.entry(CBM, 0d),
            Map.entry(AMC, 0d),
            Map.entry(CC, 0d)
            //TODO choose useful default values
    );
}

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
public class DesignSettingsReader {
    
    private QFDesignSettings qfDesignSettings;
    
    public DesignSettings readDesignSettings() {
        DesignSettings designSettings = DesignSettings.builder().build();
        
        try {
            designSettings.setAmc(Double.parseDouble(qfDesignSettings.getAmcMin()), Double.parseDouble(qfDesignSettings.getAmcMax()));
            designSettings.setCa(Double.parseDouble(qfDesignSettings.getCaMin()), Double.parseDouble(qfDesignSettings.getCaMax()));
            designSettings.setCam(Double.parseDouble(qfDesignSettings.getCamMin()), Double.parseDouble(qfDesignSettings.getCamMax()));
            designSettings.setCbm(Double.parseDouble(qfDesignSettings.getCbmMin()), Double.parseDouble(qfDesignSettings.getCbmMax()));
            designSettings.setCbo(Double.parseDouble(qfDesignSettings.getCboMin()), Double.parseDouble(qfDesignSettings.getCboMax()));
            designSettings.setCc(Double.parseDouble(qfDesignSettings.getCcMin()), Double.parseDouble(qfDesignSettings.getCcMax()));
            designSettings.setCe(Double.parseDouble(qfDesignSettings.getCeMin()), Double.parseDouble(qfDesignSettings.getCeMax()));
            designSettings.setCis(Double.parseDouble(qfDesignSettings.getCisMin()), Double.parseDouble(qfDesignSettings.getCisMax()));
            designSettings.setDam(Double.parseDouble(qfDesignSettings.getDamMin()), Double.parseDouble(qfDesignSettings.getDamMax()));
            designSettings.setDit(Double.parseDouble(qfDesignSettings.getDitMin()), Double.parseDouble(qfDesignSettings.getDitMax()));
            designSettings.setIc(Double.parseDouble(qfDesignSettings.getIcMin()), Double.parseDouble(qfDesignSettings.getIcMax()));
            designSettings.setLcom(Double.parseDouble(qfDesignSettings.getLcomMin()), Double.parseDouble(qfDesignSettings.getLcomMax()));
            designSettings.setLcom3(Double.parseDouble(qfDesignSettings.getLcom3Min()), Double.parseDouble(qfDesignSettings.getLcom3Max()));
            designSettings.setLoc(Double.parseDouble(qfDesignSettings.getLocMin()), Double.parseDouble(qfDesignSettings.getLocMax()));
            designSettings.setMfa(Double.parseDouble(qfDesignSettings.getMfaMin()), Double.parseDouble(qfDesignSettings.getMfaMax()));
            designSettings.setMoa(Double.parseDouble(qfDesignSettings.getMoaMin()), Double.parseDouble(qfDesignSettings.getMoaMax()));
            designSettings.setNoc(Double.parseDouble(qfDesignSettings.getNocMin()), Double.parseDouble(qfDesignSettings.getNocMax()));
            designSettings.setRfc(Double.parseDouble(qfDesignSettings.getRfcMin()), Double.parseDouble(qfDesignSettings.getRfcMax()));
            designSettings.setWmc(Double.parseDouble(qfDesignSettings.getWmcMin()), Double.parseDouble(qfDesignSettings.getWmcMax()));

        } catch (IllegalArgumentException | NullPointerException e) {
            LogManager.getLogger(getClass()).warn(e.getMessage());
        }
        return designSettings;
    }

    /*//@Builder.Default If used, IDE keeps getting stuck on parsing when compiling
    private Map<Metric, Double> metricsThresholds = Map.ofEntries(
            Map.entry(AMC, AMC.getInitialValue()),
            Map.entry(CA, CA.getInitialValue()),
            Map.entry(CAM, CAM.getInitialValue()),
            Map.entry(CBM, CBM.getInitialValue()),
            Map.entry(CBO, CBO.getInitialValue()),
            Map.entry(CC, CC.getInitialValue()),
            Map.entry(CE, CE.getInitialValue()),
            Map.entry(CIS, CIS.getInitialValue()),
            Map.entry(DAM, DAM.getInitialValue()),
            Map.entry(DIT, DIT.getInitialValue()),
            Map.entry(IC, IC.getInitialValue()),
            Map.entry(LCOM, LCOM.getInitialValue()),
            Map.entry(LCOM3, LCOM3.getInitialValue()),
            Map.entry(LOC, LOC.getInitialValue()),
            Map.entry(MFA, MFA.getInitialValue()),
            Map.entry(MOA, MOA.getInitialValue()),
            Map.entry(NOC, NOC.getInitialValue()),
            Map.entry(NPM, NPM.getInitialValue()),
            Map.entry(RFC, RFC.getInitialValue()),
            Map.entry(WMC, WMC.getInitialValue())
    );
    */

    /**
     * Method configuring a specific key-value-pair in the {@link #metricsThresholds}.
     *
     * @param metric the metric (key) whose value is to be set
     * @param value  the given metric's value who is to bet set (value)
     */
    /*public void configure(Metric metric, double value) {
        this.metricsThresholds.put(metric, value);
    }

     */
}

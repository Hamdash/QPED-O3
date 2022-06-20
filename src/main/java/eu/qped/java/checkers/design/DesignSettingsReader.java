package eu.qped.java.checkers.design;

import eu.qped.java.checkers.mass.QFDesignSettings;
import lombok.*;
import org.apache.logging.log4j.LogManager;

/**
 * Class representing a reader for the {@link QFDesignSettings}'s input from the user.
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

    /**
     * Method reads the input design settings from {@link #qfDesignSettings} and
     * checks (implicitly) for possible invalid values through the getter and parser.
     *
     * @return the created {@link DesignSettings} object from an initial {@link QFDesignSettings} obbject.
     */
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
}

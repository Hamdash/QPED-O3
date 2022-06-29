package eu.qped.java.checkers.design.configuration;

import eu.qped.java.checkers.mass.QFDesignSettings;
import lombok.*;
import org.apache.logging.log4j.LogManager;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;
import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.Metric.*;

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
     * @return the created {@link DesignSettings} object from an initial {@link QFDesignSettings} object.
     */
    public DesignSettings readDesignSettings() {
        DesignSettings designSettings = DesignSettings.builder().build();

        try {
            designSettings.setAmc(new MetricThreshold(AMC, getInputMetricThresholdValue(AMC, true), getInputMetricThresholdValue(AMC, false)));
            designSettings.setCa(new MetricThreshold(CA, getInputMetricThresholdValue(CA, true), getInputMetricThresholdValue(CA, false)));
            designSettings.setCam(new MetricThreshold(CAM, getInputMetricThresholdValue(CAM, true), getInputMetricThresholdValue(CAM, false)));
            designSettings.setCbm(new MetricThreshold(CBM, getInputMetricThresholdValue(CBM, true), getInputMetricThresholdValue(CBM, false)));
            designSettings.setCbo(new MetricThreshold(CBO, getInputMetricThresholdValue(CBO, true), getInputMetricThresholdValue(CBO, false)));
            designSettings.setCc(new MetricThreshold(CC, getInputMetricThresholdValue(CC, true), getInputMetricThresholdValue(CC, false)));
            designSettings.setCe(new MetricThreshold(CE, getInputMetricThresholdValue(CE, true), getInputMetricThresholdValue(CE, false)));
            designSettings.setDam(new MetricThreshold(DAM, getInputMetricThresholdValue(DAM, true), getInputMetricThresholdValue(DAM, false)));
            designSettings.setDit(new MetricThreshold(DIT, getInputMetricThresholdValue(DIT, true), getInputMetricThresholdValue(DIT, false)));
            designSettings.setIc(new MetricThreshold(IC, getInputMetricThresholdValue(IC, true), getInputMetricThresholdValue(IC, false)));
            designSettings.setLcom(new MetricThreshold(LCOM, getInputMetricThresholdValue(LCOM, true), getInputMetricThresholdValue(LCOM, false)));
            designSettings.setLcom3(new MetricThreshold(LCOM3, getInputMetricThresholdValue(LCOM3, true), getInputMetricThresholdValue(LCOM3, false)));
            designSettings.setLoc(new MetricThreshold(LOC, getInputMetricThresholdValue(LOC, true), getInputMetricThresholdValue(LOC, false)));
            designSettings.setMfa(new MetricThreshold(MFA, getInputMetricThresholdValue(MFA, true), getInputMetricThresholdValue(MFA, false)));
            designSettings.setMoa(new MetricThreshold(MOA, getInputMetricThresholdValue(MOA, true), getInputMetricThresholdValue(MOA, false)));
            designSettings.setNoc(new MetricThreshold(NOC, getInputMetricThresholdValue(NOC, true), getInputMetricThresholdValue(NOC, false)));
            designSettings.setNpm(new MetricThreshold(NPM, getInputMetricThresholdValue(NPM, true), getInputMetricThresholdValue(NPM, false)));
            designSettings.setRfc(new MetricThreshold(RFC, getInputMetricThresholdValue(RFC, true), getInputMetricThresholdValue(RFC, false)));
            designSettings.setWmc(new MetricThreshold(WMC, getInputMetricThresholdValue(WMC, true), getInputMetricThresholdValue(WMC, false)));

        } catch (IllegalArgumentException | NullPointerException e) {
            LogManager.getLogger(getClass()).warn(e.getMessage());
        }
        return designSettings;
    }

    /**
     * Method to get the input threshold of a given threshold.
     *
     * @param metric the given metric
     * @param min decides whether to return a minimum (true) or maximum (false) threshold
     * @return the threshold, -1 as error code
     */
    private double getInputMetricThresholdValue(Metric metric, boolean min) {
        double metricThreshold;
        if (min) {
            metricThreshold = getMetricThMin(metric);
        } else {
            metricThreshold = getMetricThMax(metric);
        }
        return metricThreshold;
    }

    /**
     * Helper to get the maximum threshold of a metric.
     *
     * @param metric given metric
     * @return the maximum threshold of a given metric
     */
    private double getMetricThMax(Metric metric) {
        double metricUpperThreshold = -1;
        switch (metric) {
            case AMC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getAmcMax());
                break;
            case CAM:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getCamMax());
                break;
            case CA:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getCaMax());
                break;
            case CBM:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getCbmMax());
                break;
            case CBO:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getCboMax());
                break;
            case CC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getCcMax());
                break;
            case CE:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getCeMax());
                break;
            case DAM:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getDamMax());
                break;
            case DIT:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getDitMax());
                break;
            case IC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getIcMax());
                break;
            case LCOM:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getLcomMax());
                break;
            case LCOM3:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getLcom3Max());
                break;
            case LOC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getLocMax());
                break;
            case MOA:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getMoaMax());
                break;
            case MFA:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getMfaMax());
                break;
            case NOC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getNocMax());
                break;
            case NPM:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getNpmMax());
                break;
            case RFC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getRfcMax());
                break;
            case WMC:
                metricUpperThreshold = Double.parseDouble(qfDesignSettings.getWmcMax());
                break;
        }
        return metricUpperThreshold;
    }

    /**
     * Helper to get the minimum threshold of a metric.
     *
     * @param metric given metric
     * @return the maximum threshold of given metric
     */
    private double getMetricThMin(Metric metric) {
        double metricLowerThreshold = -1;
        switch (metric) {

            case AMC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getAmcMin());
                break;
            case CAM:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getCamMin());
                break;
            case CA:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getCaMin());
                break;
            case CBM:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getCbmMin());
                break;
            case CBO:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getCboMin());
                break;
            case CC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getCcMin());
                break;
            case CE:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getCeMin());
                break;
            case DAM:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getDamMin());
                break;
            case DIT:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getDitMin());
                break;
            case IC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getIcMin());
                break;
            case LCOM:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getLcomMin());
                break;
            case LCOM3:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getLcom3Min());
                break;
            case LOC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getLocMin());
                break;
            case MOA:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getMoaMin());
                break;
            case MFA:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getMfaMin());
                break;
            case NOC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getNocMin());
                break;
            case NPM:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getNpmMin());
                break;
            case RFC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getRfcMin());
                break;
            case WMC:
                metricLowerThreshold = Double.parseDouble(qfDesignSettings.getWmcMin());
                break;
        }
        return metricLowerThreshold;
    }
}

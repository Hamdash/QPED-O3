package eu.qped.java.checkers.design.configuration;

import eu.qped.java.checkers.design.DesignSuggestion;
import eu.qped.java.checkers.mass.QFDesignSettings;
import lombok.*;

import java.util.HashMap;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;
import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.Metric.*;

/**
 * Class representing a reader for the {@link QFDesignSettings}'s input from the user.
 *
 * @author Jannik Seus
 */
@Builder
public class DesignSettingsReader {

    private QFDesignSettings qfDesignSettings;

    /**
     * Method reads the input Quarterfall design settings from {@link #qfDesignSettings} and
     * checks (implicitly) for possible invalid values through the getter and parser.
     *
     * @param designSettings the given Design Settings
     * @return the created {@link DesignSettings} object from an initial {@link QFDesignSettings} object.
     */
    public DesignSettings readDesignSettings(DesignSettings designSettings) {

        designSettings.includeCallsToJdk(Boolean.parseBoolean(qfDesignSettings.areCallsToJdkIncluded()));
        designSettings.includeOnlyPublicClasses(Boolean.parseBoolean(qfDesignSettings.areOnlyPublicClassesIncluded()));
        designSettings.setCustomSuggestions(readCustomSuggestions());
        designSettings.setAmcThreshold(retrieveMetricThreshold(AMC));
        designSettings.setCaThreshold(retrieveMetricThreshold(CA));
        designSettings.setCamThreshold(retrieveMetricThreshold(CAM));
        designSettings.setCbmThreshold(retrieveMetricThreshold(CBM));
        designSettings.setCboThreshold(retrieveMetricThreshold(CBO));
        designSettings.setCcThreshold(retrieveMetricThreshold(CC));
        designSettings.setCeThreshold(retrieveMetricThreshold(CE));
        designSettings.setDamThreshold(retrieveMetricThreshold(DAM));
        designSettings.setDitThreshold(retrieveMetricThreshold(DIT));
        designSettings.setIcThreshold(retrieveMetricThreshold(IC));
        designSettings.setLcomThreshold(retrieveMetricThreshold(LCOM));
        designSettings.setLcom3Threshold(retrieveMetricThreshold(LCOM3));
        designSettings.setLocThreshold(retrieveMetricThreshold(LOC));
        designSettings.setMfaThreshold(retrieveMetricThreshold(MFA));
        designSettings.setMoaThreshold(retrieveMetricThreshold(MOA));
        designSettings.setNocThreshold(retrieveMetricThreshold(NOC));
        designSettings.setNpmThreshold(retrieveMetricThreshold(NPM));
        designSettings.setRfcThreshold(retrieveMetricThreshold(RFC));
        designSettings.setWmcThreshold(retrieveMetricThreshold(WMC));

        return designSettings;
    }

    /**
     * Reads the custom suggestions for metrics (if set).
     * @return the resulting map
     */
    private HashMap<Metric, DesignSuggestion> readCustomSuggestions() {
        HashMap<Metric, DesignSuggestion> suggestions = new HashMap<>();
        suggestions.put(AMC, new DesignSuggestion(qfDesignSettings.getAmcCustomSuggestionLower(), qfDesignSettings.getAmcCustomSuggestionUpper()));
        suggestions.put(CA, new DesignSuggestion(qfDesignSettings.getCaCustomSuggestionLower(), qfDesignSettings.getCaCustomSuggestionUpper()));
        suggestions.put(CAM, new DesignSuggestion(qfDesignSettings.getCamCustomSuggestionLower(), qfDesignSettings.getCamCustomSuggestionUpper()));
        suggestions.put(CBM, new DesignSuggestion(qfDesignSettings.getCbmCustomSuggestionLower(), qfDesignSettings.getCbmCustomSuggestionUpper()));
        suggestions.put(CBO, new DesignSuggestion(qfDesignSettings.getCboCustomSuggestionLower(), qfDesignSettings.getCboCustomSuggestionUpper()));
        suggestions.put(CC, new DesignSuggestion(qfDesignSettings.getCcCustomSuggestionLower(), qfDesignSettings.getCcCustomSuggestionUpper()));
        suggestions.put(CE, new DesignSuggestion(qfDesignSettings.getCeCustomSuggestionLower(), qfDesignSettings.getCeCustomSuggestionUpper()));
        suggestions.put(DAM, new DesignSuggestion(qfDesignSettings.getDamCustomSuggestionLower(), qfDesignSettings.getDamCustomSuggestionUpper()));
        suggestions.put(DIT, new DesignSuggestion(qfDesignSettings.getDitCustomSuggestionLower(), qfDesignSettings.getDitCustomSuggestionUpper()));
        suggestions.put(IC, new DesignSuggestion(qfDesignSettings.getIcCustomSuggestionLower(), qfDesignSettings.getIcCustomSuggestionUpper()));
        suggestions.put(LCOM, new DesignSuggestion(qfDesignSettings.getLcomCustomSuggestionLower(), qfDesignSettings.getLcomCustomSuggestionUpper()));
        suggestions.put(LCOM3, new DesignSuggestion(qfDesignSettings.getLcom3CustomSuggestionLower(), qfDesignSettings.getLcom3CustomSuggestionUpper()));
        suggestions.put(LOC, new DesignSuggestion(qfDesignSettings.getLocCustomSuggestionLower(), qfDesignSettings.getLocCustomSuggestionUpper()));
        suggestions.put(MFA, new DesignSuggestion(qfDesignSettings.getMfaCustomSuggestionLower(), qfDesignSettings.getMfaCustomSuggestionUpper()));
        suggestions.put(MOA, new DesignSuggestion(qfDesignSettings.getMoaCustomSuggestionLower(), qfDesignSettings.getMoaCustomSuggestionUpper()));
        suggestions.put(NOC, new DesignSuggestion(qfDesignSettings.getNocCustomSuggestionLower(), qfDesignSettings.getNocCustomSuggestionUpper()));
        suggestions.put(NPM, new DesignSuggestion(qfDesignSettings.getNpmCustomSuggestionLower(), qfDesignSettings.getNpmCustomSuggestionUpper()));
        suggestions.put(RFC, new DesignSuggestion(qfDesignSettings.getRfcCustomSuggestionLower(), qfDesignSettings.getRfcCustomSuggestionUpper()));
        suggestions.put(WMC, new DesignSuggestion(qfDesignSettings.getWmcCustomSuggestionLower(), qfDesignSettings.getWmcCustomSuggestionUpper()));
        return suggestions;
    }

    /**
     * Retrieves the MetricThreshold of a given metric depending on set min and max values.
     *
     * @param metric the given metric
     * @return the created MetricThreshold
     */
    private MetricThreshold retrieveMetricThreshold(Metric metric) {
        MetricThreshold metricThreshold;
        double metricMinimum = retrieveMetricMinimum(metric);
        double metricMaximum = retrieveMetricMaximum(metric);

        if (metricMinimum == -1 && metricMaximum == -1) {
            metricThreshold = new MetricThreshold(metric);
        } else if (metricMinimum == -1) {
            metricThreshold = new MetricThreshold(metric, metricMaximum, false);
        } else if (metricMaximum == -1) {
            metricThreshold = new MetricThreshold(metric, metricMinimum, true);
        } else {
            metricThreshold = new MetricThreshold(metric, metricMinimum, metricMaximum);
        }
        return metricThreshold;
    }

    /**
     * Helper to get the maximum value for a metric.
     *
     * @param metric given metric
     * @return the maximum threshold of a given metric, -1 if an exception occurred while parsing
     */
    private double retrieveMetricMaximum(Metric metric) {
        Double metricUpperThreshold = null;

        try {
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
        } catch (NumberFormatException | NullPointerException e) {
            metricUpperThreshold = -1d;
        }
        return metricUpperThreshold;
    }

    /**
     * Helper to get the minimum value for a metric.
     *
     * @param metric given metric
     * @return the maximum threshold of given metric, -1 if an exception occurred while parsing
     */
    private double retrieveMetricMinimum(Metric metric) {
        Double metricLowerThreshold = null;
        try {
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
        } catch (NumberFormatException | NullPointerException e) {
            metricLowerThreshold = -1d;
        }
        return metricLowerThreshold;
    }
}

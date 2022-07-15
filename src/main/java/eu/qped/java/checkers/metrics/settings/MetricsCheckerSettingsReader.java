package eu.qped.java.checkers.metrics.settings;

import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerSuggestion;
import eu.qped.java.checkers.mass.QFMetricsSettings;
import lombok.*;

import java.util.HashMap;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.*;
import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;

/**
 * Class representing a reader for the {@link QFMetricsSettings}'s input from the user.
 *
 * @author Jannik Seus
 */
@Builder
public class MetricsCheckerSettingsReader {

    private QFMetricsSettings qfMetricsSettings;

    /**
     * Method reads the input Quarterfall design settings from {@link #qfMetricsSettings} and
     * checks (implicitly) for possible invalid values through the getter and parser.
     *
     * @param metricsCheckerSettings the given Design Settings
     * @return the created {@link MetricsCheckerSettings} object from an initial {@link QFMetricsSettings} object.
     */
    public MetricsCheckerSettings readMetricsCheckerSettings(MetricsCheckerSettings metricsCheckerSettings) {

        metricsCheckerSettings.includeCallsToJdk(Boolean.parseBoolean(qfMetricsSettings.areCallsToJdkIncluded()));
        metricsCheckerSettings.includeOnlyPublicClasses(Boolean.parseBoolean(qfMetricsSettings.areOnlyPublicClassesIncluded()));
        metricsCheckerSettings.setCustomSuggestions(readCustomSuggestions());
        metricsCheckerSettings.setAmcConfig(createMetricConfig(AMC));
        metricsCheckerSettings.setCaConfig(createMetricConfig(CA));
        metricsCheckerSettings.setCamConfig(createMetricConfig(CAM));
        metricsCheckerSettings.setCbmConfig(createMetricConfig(CBM));
        metricsCheckerSettings.setCboConfig(createMetricConfig(CBO));
        metricsCheckerSettings.setCcConfig(createMetricConfig(CC));
        metricsCheckerSettings.setCeConfig(createMetricConfig(CE));
        metricsCheckerSettings.setDamConfig(createMetricConfig(DAM));
        metricsCheckerSettings.setDitConfig(createMetricConfig(DIT));
        metricsCheckerSettings.setIcConfig(createMetricConfig(IC));
        metricsCheckerSettings.setLcomConfig(createMetricConfig(LCOM));
        metricsCheckerSettings.setLcom3Config(createMetricConfig(LCOM3));
        metricsCheckerSettings.setLocConfig(createMetricConfig(LOC));
        metricsCheckerSettings.setMfaConfig(createMetricConfig(MFA));
        metricsCheckerSettings.setMoaConfig(createMetricConfig(MOA));
        metricsCheckerSettings.setNocConfig(createMetricConfig(NOC));
        metricsCheckerSettings.setNpmConfig(createMetricConfig(NPM));
        metricsCheckerSettings.setRfcConfig(createMetricConfig(RFC));
        metricsCheckerSettings.setWmcConfig(createMetricConfig(WMC));

        return metricsCheckerSettings;
    }

    /**
     * Creates a {@link MetricConfig} object from a given metric.
     *
     * @param metric the given metric
     * @return the created {@link MetricConfig} object.
     * @throws IllegalArgumentException when the given parameter does not match any branch
     */
    private MetricConfig createMetricConfig(Metric metric) {
        switch (metric) {

            case AMC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getAmcCustomSuggestionLower(), qfMetricsSettings.getAmcCustomSuggestionUpper()));
            case CA:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getCaCustomSuggestionLower(), qfMetricsSettings.getCaCustomSuggestionUpper()));
            case CAM:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getCamCustomSuggestionLower(), qfMetricsSettings.getCamCustomSuggestionUpper()));
            case CBM:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getCbmCustomSuggestionLower(), qfMetricsSettings.getCbmCustomSuggestionUpper()));
            case CBO:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getCboCustomSuggestionLower(), qfMetricsSettings.getCboCustomSuggestionUpper()));
            case CC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getCcCustomSuggestionLower(), qfMetricsSettings.getCcCustomSuggestionUpper()));
            case CE:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getCeCustomSuggestionLower(), qfMetricsSettings.getCeCustomSuggestionUpper()));
            case DAM:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getDamCustomSuggestionLower(), qfMetricsSettings.getDamCustomSuggestionUpper()));
            case DIT:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getDitCustomSuggestionLower(), qfMetricsSettings.getDitCustomSuggestionUpper()));
            case IC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getIcCustomSuggestionLower(), qfMetricsSettings.getIcCustomSuggestionUpper()));
            case LCOM:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getLcomCustomSuggestionLower(), qfMetricsSettings.getLcomCustomSuggestionUpper()));
            case LCOM3:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getLcom3CustomSuggestionLower(), qfMetricsSettings.getLcom3CustomSuggestionUpper()));
            case LOC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getLocCustomSuggestionLower(), qfMetricsSettings.getLocCustomSuggestionUpper()));
            case MFA:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getMfaCustomSuggestionLower(), qfMetricsSettings.getMfaCustomSuggestionUpper()));
            case MOA:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getMoaCustomSuggestionLower(), qfMetricsSettings.getMoaCustomSuggestionUpper()));
            case NOC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getNocCustomSuggestionLower(), qfMetricsSettings.getNocCustomSuggestionUpper()));
            case NPM:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getNpmCustomSuggestionLower(), qfMetricsSettings.getNpmCustomSuggestionUpper()));
            case RFC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getRfcCustomSuggestionLower(), qfMetricsSettings.getRfcCustomSuggestionUpper()));
            case WMC:
                return new MetricConfig(retrieveMetricThreshold(metric), new MetricsCheckerSuggestion(qfMetricsSettings.getWmcCustomSuggestionLower(), qfMetricsSettings.getWmcCustomSuggestionUpper()));
            default:
                throw new IllegalArgumentException("Illegal Metric type.");
        }
    }

    /**
     * Reads the custom suggestions for metrics (if set).
     *
     * @return the resulting map
     */
    private HashMap<Metric, MetricsCheckerSuggestion> readCustomSuggestions() {
        HashMap<Metric, MetricsCheckerSuggestion> suggestions = new HashMap<>();
        suggestions.put(AMC, new MetricsCheckerSuggestion(qfMetricsSettings.getAmcCustomSuggestionLower(), qfMetricsSettings.getAmcCustomSuggestionUpper()));
        suggestions.put(CA, new MetricsCheckerSuggestion(qfMetricsSettings.getCaCustomSuggestionLower(), qfMetricsSettings.getCaCustomSuggestionUpper()));
        suggestions.put(CAM, new MetricsCheckerSuggestion(qfMetricsSettings.getCamCustomSuggestionLower(), qfMetricsSettings.getCamCustomSuggestionUpper()));
        suggestions.put(CBM, new MetricsCheckerSuggestion(qfMetricsSettings.getCbmCustomSuggestionLower(), qfMetricsSettings.getCbmCustomSuggestionUpper()));
        suggestions.put(CBO, new MetricsCheckerSuggestion(qfMetricsSettings.getCboCustomSuggestionLower(), qfMetricsSettings.getCboCustomSuggestionUpper()));
        suggestions.put(CC, new MetricsCheckerSuggestion(qfMetricsSettings.getCcCustomSuggestionLower(), qfMetricsSettings.getCcCustomSuggestionUpper()));
        suggestions.put(CE, new MetricsCheckerSuggestion(qfMetricsSettings.getCeCustomSuggestionLower(), qfMetricsSettings.getCeCustomSuggestionUpper()));
        suggestions.put(DAM, new MetricsCheckerSuggestion(qfMetricsSettings.getDamCustomSuggestionLower(), qfMetricsSettings.getDamCustomSuggestionUpper()));
        suggestions.put(DIT, new MetricsCheckerSuggestion(qfMetricsSettings.getDitCustomSuggestionLower(), qfMetricsSettings.getDitCustomSuggestionUpper()));
        suggestions.put(IC, new MetricsCheckerSuggestion(qfMetricsSettings.getIcCustomSuggestionLower(), qfMetricsSettings.getIcCustomSuggestionUpper()));
        suggestions.put(LCOM, new MetricsCheckerSuggestion(qfMetricsSettings.getLcomCustomSuggestionLower(), qfMetricsSettings.getLcomCustomSuggestionUpper()));
        suggestions.put(LCOM3, new MetricsCheckerSuggestion(qfMetricsSettings.getLcom3CustomSuggestionLower(), qfMetricsSettings.getLcom3CustomSuggestionUpper()));
        suggestions.put(LOC, new MetricsCheckerSuggestion(qfMetricsSettings.getLocCustomSuggestionLower(), qfMetricsSettings.getLocCustomSuggestionUpper()));
        suggestions.put(MFA, new MetricsCheckerSuggestion(qfMetricsSettings.getMfaCustomSuggestionLower(), qfMetricsSettings.getMfaCustomSuggestionUpper()));
        suggestions.put(MOA, new MetricsCheckerSuggestion(qfMetricsSettings.getMoaCustomSuggestionLower(), qfMetricsSettings.getMoaCustomSuggestionUpper()));
        suggestions.put(NOC, new MetricsCheckerSuggestion(qfMetricsSettings.getNocCustomSuggestionLower(), qfMetricsSettings.getNocCustomSuggestionUpper()));
        suggestions.put(NPM, new MetricsCheckerSuggestion(qfMetricsSettings.getNpmCustomSuggestionLower(), qfMetricsSettings.getNpmCustomSuggestionUpper()));
        suggestions.put(RFC, new MetricsCheckerSuggestion(qfMetricsSettings.getRfcCustomSuggestionLower(), qfMetricsSettings.getRfcCustomSuggestionUpper()));
        suggestions.put(WMC, new MetricsCheckerSuggestion(qfMetricsSettings.getWmcCustomSuggestionLower(), qfMetricsSettings.getWmcCustomSuggestionUpper()));
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
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getAmcMax());
                    break;
                case CAM:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getCamMax());
                    break;
                case CA:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getCaMax());
                    break;
                case CBM:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getCbmMax());
                    break;
                case CBO:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getCboMax());
                    break;
                case CC:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getCcMax());
                    break;
                case CE:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getCeMax());
                    break;
                case DAM:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getDamMax());
                    break;
                case DIT:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getDitMax());
                    break;
                case IC:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getIcMax());
                    break;
                case LCOM:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getLcomMax());
                    break;
                case LCOM3:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getLcom3Max());
                    break;
                case LOC:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getLocMax());
                    break;
                case MOA:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getMoaMax());
                    break;
                case MFA:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getMfaMax());
                    break;
                case NOC:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getNocMax());
                    break;
                case NPM:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getNpmMax());
                    break;
                case RFC:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getRfcMax());
                    break;
                case WMC:
                    metricUpperThreshold = Double.parseDouble(qfMetricsSettings.getWmcMax());
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
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getAmcMin());
                    break;
                case CAM:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getCamMin());
                    break;
                case CA:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getCaMin());
                    break;
                case CBM:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getCbmMin());
                    break;
                case CBO:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getCboMin());
                    break;
                case CC:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getCcMin());
                    break;
                case CE:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getCeMin());
                    break;
                case DAM:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getDamMin());
                    break;
                case DIT:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getDitMin());
                    break;
                case IC:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getIcMin());
                    break;
                case LCOM:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getLcomMin());
                    break;
                case LCOM3:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getLcom3Min());
                    break;
                case LOC:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getLocMin());
                    break;
                case MOA:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getMoaMin());
                    break;
                case MFA:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getMfaMin());
                    break;
                case NOC:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getNocMin());
                    break;
                case NPM:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getNpmMin());
                    break;
                case RFC:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getRfcMin());
                    break;
                case WMC:
                    metricLowerThreshold = Double.parseDouble(qfMetricsSettings.getWmcMin());
                    break;
            }
        } catch (NumberFormatException | NullPointerException e) {
            metricLowerThreshold = -1d;
        }
        return metricLowerThreshold;
    }
}

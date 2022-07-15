package eu.qped.java.checkers.metrics.settings;

import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerSuggestion;
import eu.qped.java.checkers.metrics.utils.MetricsCheckerTestUtility;
import eu.qped.java.checkers.mass.QFMetricsSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test class for {@link MetricsCheckerSettings}.
 *
 * @author Jannik Seus
 */
class MetricsCheckerSettingsTest {

    private MetricsCheckerSettings metricsCheckerSettings;

    @BeforeEach
    void setUp() {
        this.metricsCheckerSettings = MetricsCheckerSettings.builder().build();
    }

    @Test
    void setQfMetricsSettingsTest() {
        QFMetricsSettings qfMetricsSettings = MetricsCheckerTestUtility.generateQMetricsSettings();

        metricsCheckerSettings.setAmcConfig(new MetricConfig(new MetricThreshold(Metric.AMC, Double.parseDouble(qfMetricsSettings.getAmcMin()), Double.parseDouble(qfMetricsSettings.getAmcMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getAmcConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getAmcConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getAmcCustomSuggestionUpper(), "AMC CUSTOM SUGGESTION UPPER BOUND");

        metricsCheckerSettings.setCaConfig(new MetricConfig(new MetricThreshold(Metric.CA, Double.parseDouble(qfMetricsSettings.getCaMin()), Double.parseDouble(qfMetricsSettings.getCaMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getCaConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCaConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));


        metricsCheckerSettings.setCamConfig(new MetricConfig(new MetricThreshold(Metric.CAM, Double.parseDouble(qfMetricsSettings.getCaMin()), Double.parseDouble(qfMetricsSettings.getCaMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getCamConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCamConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setCbmConfig(new MetricConfig(new MetricThreshold(Metric.CBM, Double.parseDouble(qfMetricsSettings.getCbmMin()), Double.parseDouble(qfMetricsSettings.getCbmMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getCbmConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCbmConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setCboConfig(new MetricConfig(new MetricThreshold(Metric.CBO, Double.parseDouble(qfMetricsSettings.getCboMin()), Double.parseDouble(qfMetricsSettings.getCboMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getCboConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCboConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setCcConfig(new MetricConfig(new MetricThreshold(Metric.CC, Double.parseDouble(qfMetricsSettings.getCcMin()), Double.parseDouble(qfMetricsSettings.getCcMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getCcConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCcConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getCcCustomSuggestionUpper(), "CC CUSTOM SUGGESTION UPPER BOUND");

        metricsCheckerSettings.setCeConfig(new MetricConfig(new MetricThreshold(Metric.CE, Double.parseDouble(qfMetricsSettings.getCeMin()), Double.parseDouble(qfMetricsSettings.getCeMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getCeConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCeConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setDamConfig(new MetricConfig(new MetricThreshold(Metric.DAM, Double.parseDouble(qfMetricsSettings.getDamMin()), Double.parseDouble(qfMetricsSettings.getDamMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getDamConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getDamConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setDitConfig(new MetricConfig(new MetricThreshold(Metric.DIT, Double.parseDouble(qfMetricsSettings.getDitMin()), Double.parseDouble(qfMetricsSettings.getDitMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getDitConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getDitConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setIcConfig(new MetricConfig(new MetricThreshold(Metric.IC, Double.parseDouble(qfMetricsSettings.getIcMin()), Double.parseDouble(qfMetricsSettings.getIcMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getIcConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getIcConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setLcomConfig(new MetricConfig(new MetricThreshold(Metric.LCOM, Double.parseDouble(qfMetricsSettings.getLcomMin()), Double.parseDouble(qfMetricsSettings.getLcomMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getLcomConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getLcomConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setLcom3Config(new MetricConfig(new MetricThreshold(Metric.LCOM3, Double.parseDouble(qfMetricsSettings.getLcom3Min()), Double.parseDouble(qfMetricsSettings.getLcom3Max())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getLcom3Config().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getLcom3Config().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getLcom3CustomSuggestionLower(), "LCOM3 CUSTOM SUGGESTION LOWER BOUND");

        metricsCheckerSettings.setLocConfig(new MetricConfig(new MetricThreshold(Metric.LOC, Double.parseDouble(qfMetricsSettings.getLocMin()), Double.parseDouble(qfMetricsSettings.getLocMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getLocConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getLocConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setMfaConfig(new MetricConfig(new MetricThreshold(Metric.MFA, Double.parseDouble(qfMetricsSettings.getMfaMin()), Double.parseDouble(qfMetricsSettings.getMfaMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getMfaConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getMfaConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setMoaConfig(new MetricConfig(new MetricThreshold(Metric.MOA, Double.parseDouble(qfMetricsSettings.getMoaMin()), Double.parseDouble(qfMetricsSettings.getMoaMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getMoaConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getMoaConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setNocConfig(new MetricConfig(new MetricThreshold(Metric.NOC, Double.parseDouble(qfMetricsSettings.getNocMin()), Double.parseDouble(qfMetricsSettings.getNocMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getNocConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getNocConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setNpmConfig(new MetricConfig(new MetricThreshold(Metric.NPM, Double.parseDouble(qfMetricsSettings.getNpmMin()), Double.parseDouble(qfMetricsSettings.getNpmMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getNpmConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getNpmConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setRfcConfig(new MetricConfig(new MetricThreshold(Metric.RFC, Double.parseDouble(qfMetricsSettings.getRfcMin()), Double.parseDouble(qfMetricsSettings.getRfcMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getRfcConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getRfcConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setWmcConfig(new MetricConfig(new MetricThreshold(Metric.WMC, Double.parseDouble(qfMetricsSettings.getWmcMin()), Double.parseDouble(qfMetricsSettings.getWmcMax())), new MetricsCheckerSuggestion("lower", "upper")));
        assertThat(metricsCheckerSettings.getWmcConfig().getMetricThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getWmcConfig().getMetricThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getWmcCustomSuggestionLower(), "WMC CUSTOM SUGGESTION LOWER BOUND");
    }
}
package eu.qped.java.checkers.design.configuration;

import eu.qped.java.checkers.design.DesignFeedbackGenerator;
import eu.qped.java.checkers.design.DesignSuggestion;
import eu.qped.java.checkers.design.utils.DesignTestUtility;
import eu.qped.java.checkers.mass.QFDesignSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static eu.qped.java.checkers.design.DesignFeedbackGenerator.DefaultDesignSuggestion.CA;
import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test class for {@link DesignSettings}.
 *
 * @author Jannik Seus
 */
class DesignSettingsTest {

    private DesignSettings designSettings;

    @BeforeEach
    void setUp() {
        this.designSettings = DesignSettings.builder().build();
    }

    @Test
    void setDesignSettingsTest() {
        QFDesignSettings qfDesignSettings = DesignTestUtility.generateQfDesignSettings();

        designSettings.setAmcThreshold(new MetricThreshold(Metric.AMC, Double.parseDouble(qfDesignSettings.getAmcMin()), Double.parseDouble(qfDesignSettings.getAmcMax())));
        assertThat(designSettings.getAmcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getAmcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfDesignSettings.getAmcCustomSuggestionUpper(), "AMC CUSTOM SUGGESTION UPPER BOUND");

        designSettings.setCaThreshold(new MetricThreshold(Metric.CA, Double.parseDouble(qfDesignSettings.getCaMin()), Double.parseDouble(qfDesignSettings.getCaMax())));
        assertThat(designSettings.getCaThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getCaThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));


        designSettings.setCamThreshold(new MetricThreshold(Metric.CAM, Double.parseDouble(qfDesignSettings.getCaMin()), Double.parseDouble(qfDesignSettings.getCaMax())));
        assertThat(designSettings.getCamThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getCamThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setCbmThreshold(new MetricThreshold(Metric.CBM, Double.parseDouble(qfDesignSettings.getCbmMin()), Double.parseDouble(qfDesignSettings.getCbmMax())));
        assertThat(designSettings.getCbmThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getCbmThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setCboThreshold(new MetricThreshold(Metric.CBO, Double.parseDouble(qfDesignSettings.getCboMin()), Double.parseDouble(qfDesignSettings.getCboMax())));
        assertThat(designSettings.getCboThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getCboThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setCcThreshold(new MetricThreshold(Metric.CC, Double.parseDouble(qfDesignSettings.getCcMin()), Double.parseDouble(qfDesignSettings.getCcMax())));
        assertThat(designSettings.getCcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getCcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfDesignSettings.getCcCustomSuggestionUpper(), "CC CUSTOM SUGGESTION UPPER BOUND");

        designSettings.setCeThreshold(new MetricThreshold(Metric.CE, Double.parseDouble(qfDesignSettings.getCeMin()), Double.parseDouble(qfDesignSettings.getCeMax())));
        assertThat(designSettings.getCeThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getCeThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setDamThreshold(new MetricThreshold(Metric.DAM, Double.parseDouble(qfDesignSettings.getDamMin()), Double.parseDouble(qfDesignSettings.getDamMax())));
        assertThat(designSettings.getDamThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getDamThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setDitThreshold(new MetricThreshold(Metric.DIT, Double.parseDouble(qfDesignSettings.getDitMin()), Double.parseDouble(qfDesignSettings.getDitMax())));
        assertThat(designSettings.getDitThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getDitThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setIcThreshold(new MetricThreshold(Metric.IC, Double.parseDouble(qfDesignSettings.getIcMin()), Double.parseDouble(qfDesignSettings.getIcMax())));
        assertThat(designSettings.getIcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getIcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setLcomThreshold(new MetricThreshold(Metric.LCOM, Double.parseDouble(qfDesignSettings.getLcomMin()), Double.parseDouble(qfDesignSettings.getLcomMax())));
        assertThat(designSettings.getLcomThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getLcomThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setLcom3Threshold(new MetricThreshold(Metric.LCOM3, Double.parseDouble(qfDesignSettings.getLcom3Min()), Double.parseDouble(qfDesignSettings.getLcom3Max())));
        assertThat(designSettings.getLcom3Threshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getLcom3Threshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfDesignSettings.getLcom3CustomSuggestionLower(), "LCOM3 CUSTOM SUGGESTION LOWER BOUND");

        designSettings.setLocThreshold(new MetricThreshold(Metric.LOC, Double.parseDouble(qfDesignSettings.getLocMin()), Double.parseDouble(qfDesignSettings.getLocMax())));
        assertThat(designSettings.getLocThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getLocThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setMfaThreshold(new MetricThreshold(Metric.MFA, Double.parseDouble(qfDesignSettings.getMfaMin()), Double.parseDouble(qfDesignSettings.getMfaMax())));
        assertThat(designSettings.getMfaThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getMfaThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setMoaThreshold(new MetricThreshold(Metric.MOA, Double.parseDouble(qfDesignSettings.getMoaMin()), Double.parseDouble(qfDesignSettings.getMoaMax())));
        assertThat(designSettings.getMoaThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getMoaThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setNocThreshold(new MetricThreshold(Metric.NOC, Double.parseDouble(qfDesignSettings.getNocMin()), Double.parseDouble(qfDesignSettings.getNocMax())));
        assertThat(designSettings.getNocThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getNocThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setNpmThreshold(new MetricThreshold(Metric.NPM, Double.parseDouble(qfDesignSettings.getNpmMin()), Double.parseDouble(qfDesignSettings.getNpmMax())));
        assertThat(designSettings.getNpmThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getNpmThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setRfcThreshold(new MetricThreshold(Metric.RFC, Double.parseDouble(qfDesignSettings.getRfcMin()), Double.parseDouble(qfDesignSettings.getRfcMax())));
        assertThat(designSettings.getRfcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getRfcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        designSettings.setWmcThreshold(new MetricThreshold(Metric.WMC, Double.parseDouble(qfDesignSettings.getWmcMin()), Double.parseDouble(qfDesignSettings.getWmcMax())));
        assertThat(designSettings.getWmcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(designSettings.getWmcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfDesignSettings.getWmcCustomSuggestionLower(), "WMC CUSTOM SUGGESTION LOWER BOUND");
    }
}
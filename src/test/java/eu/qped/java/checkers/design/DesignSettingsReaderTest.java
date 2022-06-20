package eu.qped.java.checkers.design;

import eu.qped.java.checkers.mass.QFDesignSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Represents a test class for {@link DesignFeedback}.
 *
 * @author Jannik Seus
 */
class DesignSettingsReaderTest {

    private DesignSettings designSettings;

    @BeforeEach
    void setUp() {
        this.designSettings = DesignSettings.builder().build();
    }

    @Test
    void readDesignSettings() {
        QFDesignSettings qfDesignSettings = generateQfDesignSettings();

        designSettings.setAmc(Double.parseDouble(qfDesignSettings.getAmcMin()), Double.parseDouble(qfDesignSettings.getAmcMax()));
        assertEquals(designSettings.getAmcMin(), 0.5);
        assertEquals(designSettings.getAmcMax(), 1.0);

        designSettings.setWmc(Double.parseDouble(qfDesignSettings.getWmcMin()), Double.parseDouble(qfDesignSettings.getWmcMax()));
        assertEquals(designSettings.getWmcMin(), 0.5);
        assertEquals(designSettings.getWmcMax(), 1.0);

    }

    private QFDesignSettings generateQfDesignSettings() {
        QFDesignSettings qfDesignSettings = new QFDesignSettings();
        qfDesignSettings.setAmc("0.5", "1.0");
        qfDesignSettings.setCa("0.5", "1.0");
        qfDesignSettings.setCam("0.5", "1.0");
        qfDesignSettings.setCbm("0.5", "1.0");
        qfDesignSettings.setCbo("0.5", "1.0");
        qfDesignSettings.setCc("0.5", "1.0");
        qfDesignSettings.setCe("0.5", "1.0");
        qfDesignSettings.setCis("0.5", "1.0");
        qfDesignSettings.setDam("0.5", "1.0");
        qfDesignSettings.setDit("0.5", "1.0");
        qfDesignSettings.setIc("0.5", "1.0");
        qfDesignSettings.setLcom("0.5", "1.0");
        qfDesignSettings.setLcom3("0.5", "1.0");
        qfDesignSettings.setLoc("0.5", "1.0");
        qfDesignSettings.setMoa("0.5", "1.0");
        qfDesignSettings.setMfa("0.5", "1.0");
        qfDesignSettings.setNoc("0.5", "1.0");
        qfDesignSettings.setNpm("0.5", "1.0");
        qfDesignSettings.setRfc("0.5", "1.0");
        qfDesignSettings.setWmc("0.5", "1.0");
        return qfDesignSettings;
    }
}
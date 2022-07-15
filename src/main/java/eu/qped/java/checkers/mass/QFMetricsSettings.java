package eu.qped.java.checkers.mass;

import eu.qped.framework.qf.QfObjectBase;
import eu.qped.java.checkers.metrics.MetricsChecker;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;

/**
 * Data class modeling metrics settings for {@link MetricsChecker}.
 * The fields are used to hold the string values from an input json file.
 * They are modeling the thresholds for the corresponding metric, custom suggestions and other settings for the checker.
 *
 * @author Jannik Seus
 */
public class QFMetricsSettings extends QfObjectBase {

    private String includeCallsToJdk = "false";

    private String includeOnlyPublicClasses = "true";

    /**
     * Average method Complexity
     */
    private String amcMin;
    private String amcMax;
    private String amcCustomSuggestionLower;
    private String amcCustomSuggestionUpper;

    /**
     * Afferent coupled classes: classes that use this class
     */
    private String caMin;
    private String caMax;
    private String caCustomSuggestionLower;
    private String caCustomSuggestionUpper;

    /**
     * Cohesion Among methods of Class
     */
    private String camMin;
    private String camMax;
    private String camCustomSuggestionLower;
    private String camCustomSuggestionUpper;


    /**
     * Coupling Between methods
     */
    private String cbmMin;
    private String cbmMax;
    private String cbmCustomSuggestionLower;
    private String cbmCustomSuggestionUpper;

    /**
     * Coupling between object classes
     */
    private String cboMin;
    private String cboMax;
    private String cboCustomSuggestionLower;
    private String cboCustomSuggestionUpper;

    /**
     * Signatures of methods and values of McCabe Cyclomatic Complexity
     */
    private String ccMin;
    private String ccMax;
    private String ccCustomSuggestionLower;
    private String ccCustomSuggestionUpper;

    /**
     * Coupled classes: classes being used by this class
     */
    private String ceMin;
    private String ceMax;
    private String ceCustomSuggestionLower;
    private String ceCustomSuggestionUpper;

    /**
     * Data Access metric
     */
    private String damMin;
    private String damMax;
    private String damCustomSuggestionLower;
    private String damCustomSuggestionUpper;


    /**
     * Depth of inheritance tree
     */
    private String ditMin;
    private String ditMax;
    private String ditCustomSuggestionLower;
    private String ditCustomSuggestionUpper;

    /**
     * Inheritance Coupling
     */
    private String icMin;
    private String icMax;
    private String icCustomSuggestionLower;
    private String icCustomSuggestionUpper;

    /**
     * Lack of cohesion in methods
     */
    private String lcomMin;
    private String lcomMax;
    private String lcomCustomSuggestionLower;
    private String lcomCustomSuggestionUpper;

    /**
     * Lack of cohesion in methods - Henderson-Sellers definition
     */
    private String lcom3Min;
    private String lcom3Max;
    private String lcom3CustomSuggestionLower;
    private String lcom3CustomSuggestionUpper;

    /**
     * Line of codes per class (minimum and maximum thresholds)
     */
    private String locMin;
    private String locMax;
    private String locCustomSuggestionLower;
    private String locCustomSuggestionUpper;

    /**
     * measure of Aggregation
     */
    private String moaMin;
    private String moaMax;
    private String moaCustomSuggestionLower;
    private String moaCustomSuggestionUpper;

    /**
     * measure of Functional Abstraction
     */
    private String mfaMin;
    private String mfaMax;
    private String mfaCustomSuggestionLower;
    private String mfaCustomSuggestionUpper;

    /**
     * Number of children
     */
    private String nocMin;
    private String nocMax;
    private String nocCustomSuggestionLower;
    private String nocCustomSuggestionUpper;

    /**
     * Number of public methods
     */
    private String npmMin;
    private String npmMax;
    private String npmCustomSuggestionLower;
    private String npmCustomSuggestionUpper;

    /**
     * Response for a Class
     */
    private String rfcMin;
    private String rfcMax;
    private String rfcCustomSuggestionLower;
    private String rfcCustomSuggestionUpper;

    /**
     * Weighted methods per class
     */
    private String wmcMin;
    private String wmcMax;
    private String wmcCustomSuggestionLower;
    private String wmcCustomSuggestionUpper;

    public String areCallsToJdkIncluded() {
        return includeCallsToJdk;
    }

    public void includeCallsToJdk(String includeCallsToJdk) {
        this.includeCallsToJdk = includeCallsToJdk;
    }

    public String areOnlyPublicClassesIncluded() {
        return includeOnlyPublicClasses;
    }

    public void includeOnlyPublicClasses(String includeOnlyPublicClasses) {
        this.includeOnlyPublicClasses = includeOnlyPublicClasses;
    }

    public String getAmcMin() {
        return amcMin;
    }

    public void setAmcMin(String amcMin) {
        this.amcMin = amcMin;
    }

    public String getAmcMax() {
        return amcMax;
    }

    public void setAmcMax(String amcMax) {
        this.amcMax = amcMax;
    }

    public String getAmcCustomSuggestionLower() {
        return amcCustomSuggestionLower;
    }

    public void setAmcCustomSuggestionLower(String amcCustomSuggestionLower) {
        this.amcCustomSuggestionLower = amcCustomSuggestionLower;
    }

    public String getAmcCustomSuggestionUpper() {
        return amcCustomSuggestionUpper;
    }

    public void setAmcCustomSuggestionUpper(String amcCustomSuggestionUpper) {
        this.amcCustomSuggestionUpper = amcCustomSuggestionUpper;
    }

    public String getCaMin() {
        return caMin;
    }

    public void setCaMin(String caMin) {
        this.caMin = caMin;
    }

    public String getCaMax() {
        return caMax;
    }

    public void setCaMax(String caMax) {
        this.caMax = caMax;
    }

    public String getCaCustomSuggestionLower() {
        return caCustomSuggestionLower;
    }

    public void setCaCustomSuggestionLower(String caCustomSuggestionLower) {
        this.caCustomSuggestionLower = caCustomSuggestionLower;
    }

    public String getCaCustomSuggestionUpper() {
        return caCustomSuggestionUpper;
    }

    public void setCaCustomSuggestionUpper(String caCustomSuggestionUpper) {
        this.caCustomSuggestionUpper = caCustomSuggestionUpper;
    }

    public String getCamMin() {
        return camMin;
    }

    public void setCamMin(String camMin) {
        this.camMin = camMin;
    }

    public String getCamMax() {
        return camMax;
    }

    public void setCamMax(String camMax) {
        this.camMax = camMax;
    }

    public String getCamCustomSuggestionLower() {
        return camCustomSuggestionLower;
    }

    public void setCamCustomSuggestionLower(String camCustomSuggestionLower) {
        this.camCustomSuggestionLower = camCustomSuggestionLower;
    }

    public String getCamCustomSuggestionUpper() {
        return camCustomSuggestionUpper;
    }

    public void setCamCustomSuggestionUpper(String camCustomSuggestionUpper) {
        this.camCustomSuggestionUpper = camCustomSuggestionUpper;
    }

    public String getCbmMin() {
        return cbmMin;
    }

    public void setCbmMin(String cbmMin) {
        this.cbmMin = cbmMin;
    }

    public String getCbmMax() {
        return cbmMax;
    }

    public void setCbmMax(String cbmMax) {
        this.cbmMax = cbmMax;
    }

    public String getCbmCustomSuggestionLower() {
        return cbmCustomSuggestionLower;
    }

    public void setCbmCustomSuggestionLower(String cbmCustomSuggestionLower) {
        this.cbmCustomSuggestionLower = cbmCustomSuggestionLower;
    }

    public String getCbmCustomSuggestionUpper() {
        return cbmCustomSuggestionUpper;
    }

    public void setCbmCustomSuggestionUpper(String cbmCustomSuggestionUpper) {
        this.cbmCustomSuggestionUpper = cbmCustomSuggestionUpper;
    }

    public String getCboMin() {
        return cboMin;
    }

    public void setCboMin(String cboMin) {
        this.cboMin = cboMin;
    }

    public String getCboMax() {
        return cboMax;
    }

    public void setCboMax(String cboMax) {
        this.cboMax = cboMax;
    }

    public String getCboCustomSuggestionLower() {
        return cboCustomSuggestionLower;
    }

    public void setCboCustomSuggestionLower(String cboCustomSuggestionLower) {
        this.cboCustomSuggestionLower = cboCustomSuggestionLower;
    }

    public String getCboCustomSuggestionUpper() {
        return cboCustomSuggestionUpper;
    }

    public void setCboCustomSuggestionUpper(String cboCustomSuggestionUpper) {
        this.cboCustomSuggestionUpper = cboCustomSuggestionUpper;
    }

    public String getCcMin() {
        return ccMin;
    }

    public void setCcMin(String ccMin) {
        this.ccMin = ccMin;
    }

    public String getCcMax() {
        return ccMax;
    }

    public void setCcMax(String ccMax) {
        this.ccMax = ccMax;
    }

    public String getCcCustomSuggestionLower() {
        return ccCustomSuggestionLower;
    }

    public void setCcCustomSuggestionLower(String ccCustomSuggestionLower) {
        this.ccCustomSuggestionLower = ccCustomSuggestionLower;
    }

    public String getCcCustomSuggestionUpper() {
        return ccCustomSuggestionUpper;
    }

    public void setCcCustomSuggestionUpper(String ccCustomSuggestionUpper) {
        this.ccCustomSuggestionUpper = ccCustomSuggestionUpper;
    }

    public String getCeMin() {
        return ceMin;
    }

    public void setCeMin(String ceMin) {
        this.ceMin = ceMin;
    }

    public String getCeMax() {
        return ceMax;
    }

    public void setCeMax(String ceMax) {
        this.ceMax = ceMax;
    }

    public String getCeCustomSuggestionLower() {
        return ceCustomSuggestionLower;
    }

    public void setCeCustomSuggestionLower(String ceCustomSuggestionLower) {
        this.ceCustomSuggestionLower = ceCustomSuggestionLower;
    }

    public String getCeCustomSuggestionUpper() {
        return ceCustomSuggestionUpper;
    }

    public void setCeCustomSuggestionUpper(String ceCustomSuggestionUpper) {
        this.ceCustomSuggestionUpper = ceCustomSuggestionUpper;
    }

    public String getDamMin() {
        return damMin;
    }

    public void setDamMin(String damMin) {
        this.damMin = damMin;
    }

    public String getDamMax() {
        return damMax;
    }

    public void setDamMax(String damMax) {
        this.damMax = damMax;
    }

    public String getDamCustomSuggestionLower() {
        return damCustomSuggestionLower;
    }

    public void setDamCustomSuggestionLower(String damCustomSuggestionLower) {
        this.damCustomSuggestionLower = damCustomSuggestionLower;
    }

    public String getDamCustomSuggestionUpper() {
        return damCustomSuggestionUpper;
    }

    public void setDamCustomSuggestionUpper(String damCustomSuggestionUpper) {
        this.damCustomSuggestionUpper = damCustomSuggestionUpper;
    }

    public String getDitMin() {
        return ditMin;
    }

    public void setDitMin(String ditMin) {
        this.ditMin = ditMin;
    }

    public String getDitMax() {
        return ditMax;
    }

    public void setDitMax(String ditMax) {
        this.ditMax = ditMax;
    }

    public String getDitCustomSuggestionLower() {
        return ditCustomSuggestionLower;
    }

    public void setDitCustomSuggestionLower(String ditCustomSuggestionLower) {
        this.ditCustomSuggestionLower = ditCustomSuggestionLower;
    }

    public String getDitCustomSuggestionUpper() {
        return ditCustomSuggestionUpper;
    }

    public void setDitCustomSuggestionUpper(String ditCustomSuggestionUpper) {
        this.ditCustomSuggestionUpper = ditCustomSuggestionUpper;
    }

    public String getIcMin() {
        return icMin;
    }

    public void setIcMin(String icMin) {
        this.icMin = icMin;
    }

    public String getIcMax() {
        return icMax;
    }

    public void setIcMax(String icMax) {
        this.icMax = icMax;
    }

    public String getIcCustomSuggestionLower() {
        return icCustomSuggestionLower;
    }

    public void setIcCustomSuggestionLower(String icCustomSuggestionLower) {
        this.icCustomSuggestionLower = icCustomSuggestionLower;
    }

    public String getIcCustomSuggestionUpper() {
        return icCustomSuggestionUpper;
    }

    public void setIcCustomSuggestionUpper(String icCustomSuggestionUpper) {
        this.icCustomSuggestionUpper = icCustomSuggestionUpper;
    }

    public String getLcomMin() {
        return lcomMin;
    }

    public void setLcomMin(String lcomMin) {
        this.lcomMin = lcomMin;
    }

    public String getLcomMax() {
        return lcomMax;
    }

    public void setLcomMax(String lcomMax) {
        this.lcomMax = lcomMax;
    }

    public String getLcomCustomSuggestionLower() {
        return lcomCustomSuggestionLower;
    }

    public void setLcomCustomSuggestionLower(String lcomCustomSuggestionLower) {
        this.lcomCustomSuggestionLower = lcomCustomSuggestionLower;
    }

    public String getLcomCustomSuggestionUpper() {
        return lcomCustomSuggestionUpper;
    }

    public void setLcomCustomSuggestionUpper(String lcomCustomSuggestionUpper) {
        this.lcomCustomSuggestionUpper = lcomCustomSuggestionUpper;
    }

    public String getLcom3Min() {
        return lcom3Min;
    }

    public void setLcom3Min(String lcom3Min) {
        this.lcom3Min = lcom3Min;
    }

    public String getLcom3Max() {
        return lcom3Max;
    }

    public void setLcom3Max(String lcom3Max) {
        this.lcom3Max = lcom3Max;
    }

    public String getLcom3CustomSuggestionLower() {
        return lcom3CustomSuggestionLower;
    }

    public void setLcom3CustomSuggestionLower(String lcom3CustomSuggestionLower) {
        this.lcom3CustomSuggestionLower = lcom3CustomSuggestionLower;
    }

    public String getLcom3CustomSuggestionUpper() {
        return lcom3CustomSuggestionUpper;
    }

    public void setLcom3CustomSuggestionUpper(String lcom3CustomSuggestionUpper) {
        this.lcom3CustomSuggestionUpper = lcom3CustomSuggestionUpper;
    }

    public String getLocMin() {
        return locMin;
    }

    public void setLocMin(String locMin) {
        this.locMin = locMin;
    }

    public String getLocMax() {
        return locMax;
    }

    public void setLocMax(String locMax) {
        this.locMax = locMax;
    }

    public String getLocCustomSuggestionLower() {
        return locCustomSuggestionLower;
    }

    public void setLocCustomSuggestionLower(String locCustomSuggestionLower) {
        this.locCustomSuggestionLower = locCustomSuggestionLower;
    }

    public String getLocCustomSuggestionUpper() {
        return locCustomSuggestionUpper;
    }

    public void setLocCustomSuggestionUpper(String locCustomSuggestionUpper) {
        this.locCustomSuggestionUpper = locCustomSuggestionUpper;
    }

    public String getMoaMin() {
        return moaMin;
    }

    public void setMoaMin(String moaMin) {
        this.moaMin = moaMin;
    }

    public String getMoaMax() {
        return moaMax;
    }

    public void setMoaMax(String moaMax) {
        this.moaMax = moaMax;
    }

    public String getMoaCustomSuggestionLower() {
        return moaCustomSuggestionLower;
    }

    public void setMoaCustomSuggestionLower(String moaCustomSuggestionLower) {
        this.moaCustomSuggestionLower = moaCustomSuggestionLower;
    }

    public String getMoaCustomSuggestionUpper() {
        return moaCustomSuggestionUpper;
    }

    public void setMoaCustomSuggestionUpper(String moaCustomSuggestionUpper) {
        this.moaCustomSuggestionUpper = moaCustomSuggestionUpper;
    }

    public String getMfaMin() {
        return mfaMin;
    }

    public void setMfaMin(String mfaMin) {
        this.mfaMin = mfaMin;
    }

    public String getMfaMax() {
        return mfaMax;
    }

    public void setMfaMax(String mfaMax) {
        this.mfaMax = mfaMax;
    }

    public String getMfaCustomSuggestionLower() {
        return mfaCustomSuggestionLower;
    }

    public void setMfaCustomSuggestionLower(String mfaCustomSuggestionLower) {
        this.mfaCustomSuggestionLower = mfaCustomSuggestionLower;
    }

    public String getMfaCustomSuggestionUpper() {
        return mfaCustomSuggestionUpper;
    }

    public void setMfaCustomSuggestionUpper(String mfaCustomSuggestionUpper) {
        this.mfaCustomSuggestionUpper = mfaCustomSuggestionUpper;
    }

    public String getNocMin() {
        return nocMin;
    }

    public void setNocMin(String nocMin) {
        this.nocMin = nocMin;
    }

    public String getNocMax() {
        return nocMax;
    }

    public void setNocMax(String nocMax) {
        this.nocMax = nocMax;
    }

    public String getNocCustomSuggestionLower() {
        return nocCustomSuggestionLower;
    }

    public void setNocCustomSuggestionLower(String nocCustomSuggestionLower) {
        this.nocCustomSuggestionLower = nocCustomSuggestionLower;
    }

    public String getNocCustomSuggestionUpper() {
        return nocCustomSuggestionUpper;
    }

    public void setNocCustomSuggestionUpper(String nocCustomSuggestionUpper) {
        this.nocCustomSuggestionUpper = nocCustomSuggestionUpper;
    }

    public String getNpmMin() {
        return npmMin;
    }

    public void setNpmMin(String npmMin) {
        this.npmMin = npmMin;
    }

    public String getNpmMax() {
        return npmMax;
    }

    public void setNpmMax(String npmMax) {
        this.npmMax = npmMax;
    }

    public String getNpmCustomSuggestionLower() {
        return npmCustomSuggestionLower;
    }

    public void setNpmCustomSuggestionLower(String npmCustomSuggestionLower) {
        this.npmCustomSuggestionLower = npmCustomSuggestionLower;
    }

    public String getNpmCustomSuggestionUpper() {
        return npmCustomSuggestionUpper;
    }

    public void setNpmCustomSuggestionUpper(String npmCustomSuggestionUpper) {
        this.npmCustomSuggestionUpper = npmCustomSuggestionUpper;
    }

    public String getRfcMin() {
        return rfcMin;
    }

    public void setRfcMin(String rfcMin) {
        this.rfcMin = rfcMin;
    }

    public String getRfcMax() {
        return rfcMax;
    }

    public void setRfcMax(String rfcMax) {
        this.rfcMax = rfcMax;
    }

    public String getRfcCustomSuggestionLower() {
        return rfcCustomSuggestionLower;
    }

    public void setRfcCustomSuggestionLower(String rfcCustomSuggestionLower) {
        this.rfcCustomSuggestionLower = rfcCustomSuggestionLower;
    }

    public String getRfcCustomSuggestionUpper() {
        return rfcCustomSuggestionUpper;
    }

    public void setRfcCustomSuggestionUpper(String rfcCustomSuggestionUpper) {
        this.rfcCustomSuggestionUpper = rfcCustomSuggestionUpper;
    }

    public String getWmcMin() {
        return wmcMin;
    }

    public void setWmcMin(String wmcMin) {
        this.wmcMin = wmcMin;
    }

    public String getWmcMax() {
        return wmcMax;
    }

    public void setWmcMax(String wmcMax) {
        this.wmcMax = wmcMax;
    }

    public String getWmcCustomSuggestionLower() {
        return wmcCustomSuggestionLower;
    }

    public void setWmcCustomSuggestionLower(String wmcCustomSuggestionLower) {
        this.wmcCustomSuggestionLower = wmcCustomSuggestionLower;
    }

    public String getWmcCustomSuggestionUpper() {
        return wmcCustomSuggestionUpper;
    }

    public void setWmcCustomSuggestionUpper(String wmcCustomSuggestionUpper) {
        this.wmcCustomSuggestionUpper = wmcCustomSuggestionUpper;
    }

    public void setLoc(String locMin, String locMax) {
        if (locMin == null) {
            this.locMin = Double.toString(LOC.getMinimum());
        } else {
            this.locMin = locMin;
        }
        if (locMax == null) {
            this.locMax = Double.toString(LOC.getMinimum());
        } else {
            this.locMax = locMax;
        }
    }

    public void setWmc(String wmcMin, String wmcMax) {
        if (wmcMin == null) {
            this.wmcMin = Double.toString(WMC.getMinimum());
        } else {
            this.wmcMin = wmcMin;
        }
        if (wmcMax == null) {
            this.wmcMax = Double.toString(WMC.getMaximum());
        } else {
            this.wmcMax = wmcMax;
        }
    }

    public void setNoc(String nocMin, String nocMax) {
        if (nocMin == null) {
            this.nocMin = Double.toString(NOC.getMinimum());
        } else {
            this.nocMin = nocMin;
        }
        if (nocMax == null) {
            this.nocMax = Double.toString(NOC.getMaximum());
        } else {
            this.nocMax = nocMax;
        }
    }

    public void setRfc(String rfcMin, String rfcMax) {
        if (rfcMin == null) {
            this.rfcMin = Double.toString(RFC.getMinimum());
        } else {
            this.rfcMin = rfcMin;
        }
        if (rfcMax == null) {
            this.rfcMax = Double.toString(RFC.getMaximum());
        } else {
            this.rfcMax = rfcMax;
        }
    }

    public void setCe(String ceMin, String ceMax) {
        if (ceMin == null) {
            this.ceMin = Double.toString(CE.getMinimum());
        } else {
            this.ceMin = ceMin;
        }
        if (ceMax == null) {
            this.ceMax = Double.toString(CE.getMaximum());
        } else {
            this.ceMax = ceMax;
        }
    }

    public void setCa(String caMin, String caMax) {
        if (caMin == null) {
            this.caMin = Double.toString(CA.getMinimum());
        } else {
            this.caMin = caMin;
        }
        if (caMax == null) {
            this.caMax = Double.toString(CA.getMaximum());
        } else {
            this.caMax = caMax;
        }
    }

    public void setDit(String ditMin, String ditMax) {
        if (ditMin == null) {
            this.ditMin = Double.toString(DIT.getMinimum());
        } else {
            this.ditMin = ditMin;
        }
        if (ditMax == null) {
            this.ditMax = Double.toString(DIT.getMaximum());
        } else {
            this.ditMax = ditMax;
        }
    }

    public void setLcom(String lcomMin, String lcomMax) {
        if (lcomMin == null) {
            this.lcomMin = Double.toString(LCOM.getMinimum());
        } else {
            this.lcomMin = lcomMin;
        }
        if (lcomMax == null) {
            this.lcomMax = Double.toString(LCOM.getMaximum());
        } else {
            this.lcomMax = lcomMax;
        }
    }

    public void setLcom3(String lcom3Min, String lcom3Max) {
        if (lcom3Min == null) {
            this.lcom3Min = Double.toString(LCOM3.getMinimum());
        } else {
            this.lcom3Min = lcom3Min;
        }
        if (lcom3Max == null) {
            this.lcom3Max = Double.toString(LCOM3.getMaximum());
        } else {
            this.lcom3Max = lcom3Max;
        }
    }

    public void setNpm(String npmMin, String npmMax) {
        if (npmMin == null) {
            this.npmMin = Double.toString(NPM.getMinimum());
        } else {
            this.npmMin = npmMin;
        }
        if (npmMax == null) {
            this.npmMax = Double.toString(NPM.getMaximum());
        } else {
            this.npmMax = npmMax;
        }
    }

    public void setCc(String ccMin, String ccMax) {
        if (ccMin == null) {
            this.ccMin = Double.toString(CC.getMinimum());
        } else {
            this.ccMin = ccMin;
        }
        if (ccMax == null) {
            this.ccMax = Double.toString(CC.getMaximum());
        } else {
            this.ccMax = ccMax;
        }
    }

    public void setDam(String damMin, String damMax) {
        if (damMin == null) {
            this.damMin = Double.toString(DAM.getMinimum());
        } else {
            this.damMin = damMin;
        }
        if (damMax == null) {
            this.damMax = Double.toString(DAM.getMaximum());
        } else {
            this.damMax = damMax;
        }
    }

    public void setMoa(String moaMin, String moaMax) {
        if (moaMin == null) {
            this.moaMin = Double.toString(MOA.getMinimum());
        } else {
            this.moaMin = moaMin;
        }
        if (moaMax == null) {
            this.moaMax = Double.toString(MOA.getMaximum());
        } else {
            this.moaMax = moaMax;
        }
    }

    public void setMfa(String mfaMin, String mfaMax) {
        if (mfaMin == null) {
            this.mfaMin = Double.toString(MFA.getMinimum());
        } else {
            this.mfaMin = mfaMin;
        }
        if (mfaMax == null) {
            this.mfaMax = Double.toString(MFA.getMaximum());
        } else {
            this.mfaMax = mfaMax;
        }
    }

    public void setCam(String camMin, String camMax) {
        if (camMin == null) {
            this.camMin = Double.toString(CAM.getMinimum());
        } else {
            this.camMin = camMin;
        }
        if (camMax == null) {
            this.camMax = Double.toString(CAM.getMaximum());
        } else {
            this.camMax = camMax;
        }
    }

    public void setIc(String icMin, String icMax) {
        if (icMin == null) {
            this.icMin = Double.toString(IC.getMinimum());
        } else {
            this.icMin = icMin;
        }
        if (icMax == null) {
            this.icMax = Double.toString(IC.getMaximum());
        } else {
            this.icMax = icMax;
        }
    }

    public void setCbm(String cbmMin, String cbmMax) {
        if (cbmMin == null) {
            this.cbmMin = Double.toString(CBM.getMinimum());
        } else {
            this.cbmMin = cbmMin;
        }
        if (cbmMax == null) {
            this.cbmMax = Double.toString(CBM.getMaximum());
        } else {
            this.cbmMax = cbmMax;
        }
    }

    public void setAmc(String amcMin, String amcMax) {
        if (amcMin == null) {
            this.amcMin = Double.toString(AMC.getMinimum());
        } else {
            this.amcMin = amcMin;
        }
        if (amcMax == null) {
            this.amcMax = Double.toString(AMC.getMaximum());
        } else {
            this.amcMax = amcMax;
        }
    }

    public void setCbo(String cboMin, String cboMax) {
        if (cboMin == null) {
            this.cboMin = Double.toString(CBO.getMinimum());
        } else {
            this.cboMin = cboMin;
        }
        if (cboMax == null) {
            this.cboMax = Double.toString(CBO.getMaximum());
        } else {
            this.cboMax = cboMax;
        }
    }


}

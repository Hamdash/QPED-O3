package eu.qped.java.checkers.mass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.qped.framework.qf.QfObjectBase;
import eu.qped.java.checkers.metrics.MetricsChecker;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;

/**
 * Data class modeling metrics settings for {@link MetricsChecker}.
 * The fields are used to hold the string values from an input json file.
 * They are modeling the thresholds for the corresponding metric, custom suggestions and other settings for the checker.
 *
 * @author Jannik Seus
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QFMetricsSettings extends QfObjectBase {

    private String includeCallsToJdk = "false";
    private String includeOnlyPublicClasses = "false";

    /**
     * Average method Complexity
     */
    private String amcMin;
    private String amcMax;
    private String amcNoMax;
    private String amcSuggestionMin;
    private String amcSuggestionMax;

    @JsonProperty("amcThreshold")
    private void amcDeserializer(Map<String, Object> amc) {
        this.amcMin = (String) amc.get("min");
        this.amcMax = (String) amc.get("max");
        this.amcNoMax = (String) amc.get("noMax");
        this.amcSuggestionMin = (String) amc.get("suggestionMin");
        this.amcSuggestionMax = (String) amc.get("suggestionMax");
    }


    /**
     * Afferent coupled classes: classes that use this class
     */
    private String caMin;
    private String caMax;
    private String caNoMax;
    private String caSuggestionMin;
    private String caSuggestionMax;

    @JsonProperty("caThreshold")
    private void caDeserializer(Map<String, Object> ca) {
        this.caMin = (String) ca.get("min");
        this.caMax = (String) ca.get("max");
        this.caNoMax = (String) ca.get("noMax");
        this.caSuggestionMin = (String) ca.get("suggestionMin");
        this.caSuggestionMax = (String) ca.get("suggestionMax");
    }

    /**
     * Cohesion Among methods of Class
     */
    private String camMin;
    private String camMax;
    private String camNoMax;
    private String camSuggestionMin;
    private String camSuggestionMax;

    @JsonProperty("camThreshold")
    private void camDeserializer(Map<String, Object> cam) {
        this.camMin = (String) cam.get("min");
        this.camMax = (String) cam.get("max");
        this.camNoMax = (String) cam.get("noMax");
        this.camSuggestionMin = (String) cam.get("suggestionMin");
        this.camSuggestionMax = (String) cam.get("suggestionMax");
    }

    /**
     * Coupling Between methods
     */
    private String cbmMin;
    private String cbmMax;
    private String cbmNoMax;
    private String cbmSuggestionMin;
    private String cbmSuggestionMax;

    @JsonProperty("cbmThreshold")
    private void cbmDeserializer(Map<String, Object> cbm) {
        this.cbmMin = (String) cbm.get("min");
        this.cbmMax = (String) cbm.get("max");
        this.cbmNoMax = (String) cbm.get("noMax");
        this.cbmSuggestionMin = (String) cbm.get("suggestionMin");
        this.cbmSuggestionMax = (String) cbm.get("suggestionMax");
    }

    /**
     * Coupling between object classes
     */
    private String cboMin;
    private String cboMax;
    private String cboNoMax;
    private String cboSuggestionMin;
    private String cboSuggestionMax;

    @JsonProperty("cboThreshold")
    private void cboDeserializer(Map<String, Object> cbo) {
        this.cboMin = (String) cbo.get("min");
        this.cboMax = (String) cbo.get("max");
        this.cboNoMax = (String) cbo.get("noMax");
        this.cboSuggestionMin = (String) cbo.get("suggestionMin");
        this.cboSuggestionMax = (String) cbo.get("suggestionMax");
    }

    /**
     * Signatures of methods and values of McCabe Cyclomatic Complexity
     */
    private String ccMin;
    private String ccMax;
    private String ccNoMax;
    private String ccSuggestionMin;
    private String ccSuggestionMax;

    @JsonProperty("ccThreshold")
    private void ccDeserializer(Map<String, Object> cc) {
        this.ccMin = (String) cc.get("min");
        this.ccMax = (String) cc.get("max");
        this.ccNoMax = (String) cc.get("noMax");
        this.ccSuggestionMin = (String) cc.get("suggestionMin");
        this.ccSuggestionMax = (String) cc.get("suggestionMax");
    }

    /**
     * Coupled classes: classes being used by this class
     */
    private String ceMin;
    private String ceMax;
    private String ceNoMax;
    private String ceSuggestionMin;
    private String ceSuggestionMax;

    @JsonProperty("ceThreshold")
    private void ceDeserializer(Map<String, Object> ce) {
        this.ceMin = (String) ce.get("min");
        this.ceMax = (String) ce.get("max");
        this.ceNoMax = (String) ce.get("noMax");
        this.ceSuggestionMin = (String) ce.get("suggestionMin");
        this.ceSuggestionMax = (String) ce.get("suggestionMax");
    }

    /**
     * Data Access metric
     */
    private String damMin;
    private String damMax;
    private String damNoMax;
    private String damSuggestionMin;
    private String damSuggestionMax;

    @JsonProperty("damThreshold")
    private void damDeserializer(Map<String, Object> dam) {
        this.damMin = (String) dam.get("min");
        this.damMax = (String) dam.get("max");
        this.damNoMax = (String) dam.get("noMax");
        this.damSuggestionMin = (String) dam.get("suggestionMin");
        this.damSuggestionMax = (String) dam.get("suggestionMax");
    }


    /**
     * Depth of inheritance tree
     */
    private String ditMin;
    private String ditMax;
    private String ditNoMax;
    private String ditSuggestionMin;
    private String ditSuggestionMax;

    @JsonProperty("ditThreshold")
    private void ditDeserializer(Map<String, Object> dit) {
        this.ditMin = (String) dit.get("min");
        this.ditMax = (String) dit.get("max");
        this.ditNoMax = (String) dit.get("noMax");
        this.ditSuggestionMin = (String) dit.get("suggestionMin");
        this.ditSuggestionMax = (String) dit.get("suggestionMax");
    }

    /**
     * Inheritance Coupling
     */
    private String icMin;
    private String icMax;
    private String icNoMax;
    private String icSuggestionMin;
    private String icSuggestionMax;

    @JsonProperty("icThreshold")
    private void icDeserializer(Map<String, Object> ic) {
        this.icMin = (String) ic.get("min");
        this.icMax = (String) ic.get("max");
        this.icNoMax = (String) ic.get("noMax");
        this.icSuggestionMin = (String) ic.get("suggestionMin");
        this.icSuggestionMax = (String) ic.get("suggestionMax");
    }

    /**
     * Lack of cohesion in methods
     */
    private String lcomMin;
    private String lcomMax;
    private String lcomNoMax;
    private String lcomSuggestionMin;
    private String lcomSuggestionMax;

    @JsonProperty("lcomThreshold")
    private void lcomDeserializer(Map<String, Object> lcom) {
        this.lcomMin = (String) lcom.get("min");
        this.lcomMax = (String) lcom.get("max");
        this.lcomNoMax = (String) lcom.get("noMax");
        this.lcomSuggestionMin = (String) lcom.get("suggestionMin");
        this.lcomSuggestionMax = (String) lcom.get("suggestionMax");
    }

    /**
     * Lack of cohesion in methods - Henderson-Sellers definition
     */
    private String lcom3Min;
    private String lcom3Max;
    private String lcom3NoMax;
    private String lcom3SuggestionMin;
    private String lcom3SuggestionMax;

    @JsonProperty("lcom3Threshold")
    private void lcom3Deserializer(Map<String, Object> lcom3) {
        this.lcom3Min = (String) lcom3.get("min");
        this.lcom3Max = (String) lcom3.get("max");
        this.lcom3NoMax = (String) lcom3.get("noMax");
        this.lcom3SuggestionMin = (String) lcom3.get("suggestionMin");
        this.lcom3SuggestionMax = (String) lcom3.get("suggestionMax");
    }

    /**
     * Line of codes per class (minimum and maximum thresholds)
     */
    private String locMin;
    private String locMax;
    private String locNoMax;
    private String locSuggestionMin;
    private String locSuggestionMax;

    @JsonProperty("locThreshold")
    private void locDeserializer(Map<String, Object> loc) {
        this.locMin = (String) loc.get("min");
        this.locMax = (String) loc.get("max");
        this.locNoMax = (String) loc.get("noMax");
        this.locSuggestionMin = (String) loc.get("suggestionMin");
        this.locSuggestionMax = (String) loc.get("suggestionMax");
    }

    /**
     * measure of Aggregation
     */
    private String moaMin;
    private String moaMax;
    private String moaNoMax;
    private String moaSuggestionMin;
    private String moaSuggestionMax;

    @JsonProperty("moaThreshold")
    private void moaDeserializer(Map<String, Object> moa) {
        this.moaMin = (String) moa.get("min");
        this.moaMax = (String) moa.get("max");
        this.moaNoMax = (String) moa.get("noMax");
        this.moaSuggestionMin = (String) moa.get("suggestionMin");
        this.moaSuggestionMax = (String) moa.get("suggestionMax");
    }

    /**
     * measure of Functional Abstraction
     */
    private String mfaMin;
    private String mfaMax;
    private String mfaNoMax;
    private String mfaSuggestionMin;
    private String mfaSuggestionMax;

    @JsonProperty("mfaThreshold")
    private void mfaDeserializer(Map<String, Object> mfa) {
        this.mfaMin = (String) mfa.get("min");
        this.mfaMax = (String) mfa.get("max");
        this.mfaNoMax = (String) mfa.get("noMax");
        this.mfaSuggestionMin = (String) mfa.get("suggestionMin");
        this.mfaSuggestionMax = (String) mfa.get("suggestionMax");
    }

    /**
     * Number of children
     */
    private String nocMin;
    private String nocMax;
    private String nocNoMax;
    private String nocSuggestionMin;
    private String nocSuggestionMax;

    @JsonProperty("nocThreshold")
    private void nocDeserializer(Map<String, Object> noc) {
        this.nocMin = (String) noc.get("min");
        this.nocMax = (String) noc.get("max");
        this.nocNoMax = (String) noc.get("noMax");
        this.nocSuggestionMin = (String) noc.get("suggestionMin");
        this.nocSuggestionMax = (String) noc.get("suggestionMax");
    }

    /**
     * Number of public methods
     */
    private String npmMin;
    private String npmMax;
    private String npmNoMax;
    private String npmSuggestionMin;
    private String npmSuggestionMax;

    @JsonProperty("npmThreshold")
    private void npmDeserializer(Map<String, Object> npm) {
        this.npmMin = (String) npm.get("min");
        this.npmMax = (String) npm.get("max");
        this.npmNoMax = (String) npm.get("noMax");
        this.npmSuggestionMin = (String) npm.get("suggestionMin");
        this.npmSuggestionMax = (String) npm.get("suggestionMax");
    }

    /**
     * Response for a Class
     */
    private String rfcMin;
    private String rfcMax;
    private String rfcNoMax;
    private String rfcSuggestionMin;
    private String rfcSuggestionMax;

    @JsonProperty("rfcThreshold")
    private void rfcDeserializer(Map<String, Object> rfc) {
        this.rfcMin = (String) rfc.get("min");
        this.rfcMax = (String) rfc.get("max");
        this.rfcNoMax = (String) rfc.get("noMax");
        this.rfcSuggestionMin = (String) rfc.get("suggestionMin");
        this.rfcSuggestionMax = (String) rfc.get("suggestionMax");
    }

    /**
     * Weighted methods per class
     */
    private String wmcMin;
    private String wmcMax;
    private String wmcNoMax;
    private String wmcSuggestionMin;
    private String wmcSuggestionMax;

    @JsonProperty("wmcThreshold")
    private void wmcDeserializer(Map<String, Object> wmc) {
        this.wmcMin = (String) wmc.get("min");
        this.wmcMax = (String) wmc.get("max");
        this.wmcNoMax = (String) wmc.get("noMax");
        this.wmcSuggestionMin = (String) wmc.get("suggestionMin");
        this.wmcSuggestionMax = (String) wmc.get("suggestionMax");
    }


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

    public void setLoc(String locMin, String locMax) {
        setLocMin(locMin);
        setLocMax(locMax);
    }

    public void setWmc(String wmcMin, String wmcMax) {
        setWmcMin(wmcMin);
        setWmcMax(wmcMax);
    }

    public void setNoc(String nocMin, String nocMax) {
        setNocMin(nocMin);
        setNocMax(nocMax);
    }

    public void setRfc(String rfcMin, String rfcMax) {
        setRfcMin(rfcMin);
        setRfcMax(rfcMax);
    }

    public void setCe(String ceMin, String ceMax) {
        setCeMin(ceMin);
        setCeMax(ceMax);
    }

    public void setCa(String caMin, String caMax) {
        setCaMin(caMin);
        setCaMax(caMax);
    }

    public void setDit(String ditMin, String ditMax) {
        setDitMin(ditMin);
        setDitMax(ditMax);
    }

    public void setLcom(String lcomMin, String lcomMax) {
        setLcomMin(lcomMin);
        setLcomMax(lcomMax);
    }

    public void setLcom3(String lcom3Min, String lcom3Max) {
        setLcom3Min(lcom3Min);
        setLcom3Max(lcom3Max);
    }

    public void setNpm(String npmMin, String npmMax) {
        setNpmMin(npmMin);
        setNpmMax(npmMax);
    }

    public void setCc(String ccMin, String ccMax) {
        setCcMin(ccMin);
        setCcMax(ccMax);
    }

    public void setDam(String damMin, String damMax) {
        setDamMin(damMin);
        setDamMax(damMax);
    }

    public void setMoa(String moaMin, String moaMax) {
        setMoaMin(moaMin);
        setMoaMax(moaMax);
    }

    public void setMfa(String mfaMin, String mfaMax) {
        setMfaMin(mfaMin);
        setMfaMax(mfaMax);
    }

    public void setCam(String camMin, String camMax) {
        setCamMin(camMin);
        setCamMax(camMax);
    }

    public void setIc(String icMin, String icMax) {
        setIcMin(icMin);
        setIcMax(icMax);
    }

    public void setCbm(String cbmMin, String cbmMax) {
        setCbmMin(cbmMin);
        setCbmMax(cbmMax);
    }

    public void setAmc(String amcMin, String amcMax) {
        setAmcMin(amcMin);
        setAmcMax(amcMax);
    }

    public void setCbo(String cboMin, String cboMax) {
        setCboMin(cboMin);
        setCboMax(cboMax);
    }
}
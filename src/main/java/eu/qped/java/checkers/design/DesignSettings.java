package eu.qped.java.checkers.design;

import eu.qped.framework.qf.QfObjectBase;
import lombok.*;

import static eu.qped.java.checkers.design.ckjm.SaveMapResults.Metric.*;

/**
 * Class modeling design settings for {@link DesignChecker}.
 * The fields are modeling the threshold for the corresponding metric.
 *
 * @author Jannik Seus
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignSettings extends QfObjectBase {


    /**
     * Line of codes per class (minimum and maximum thresholds)
     */
    private double locMin;
    private double locMax;

    /**
     * Weighted methods per class
     */
    private double wmcMin;
    private double wmcMax;

    /**
     * Number of children
     */
    private double nocMin;
    private double nocMax;

    /**
     * Response for a Class
     */
    private double rfcMin;
    private double rfcMax;

    /**
     * Coupled classes: classes being used by this class
     */
    private double ceMin;
    private double ceMax;
    /**
     * Coupled classes: classes that use this class
     */
    private double caMin;
    private double caMax;

    /**
     * Depth of inheritance tree
     */
    private double ditMin;
    private double ditMax;

    /**
     * Lack of cohesion in methods
     */
    private double lcomMin;
    private double lcomMax;
    /**
     * Lack of cohesion in methods - Henderson-Sellers definition
     */
    private double lcom3Min;
    private double lcom3Max;

    /**
     * Number of public methods
     */
    private double npmMin;
    private double npmMax;

    /**
     * Signatures of methods and values of McCabe Cyclomatic Complexity
     */
    private double ccMin;
    private double ccMax;

    /**
     * Data Access etric
     */
    private double damMin;
    private double damMax;

    /**
     * measure of Aggregation
     */
    private double moaMin;
    private double moaMax;

    /**
     * measure of Functional Abstraction
     */
    private double mfaMin;
    private double mfaMax;

    /**
     * Cohesion Among methods of Class
     */
    private double camMin;
    private double camMax;

    /**
     * Inheritance Coupling
     */
    private double icMin;
    private double icMax;

    /**
     * Coupling Between methods
     */
    private double cbmMin;
    private double cbmMax;

    /**
     * Average method Complexity
     */
    private double amcMin;
    private double amcMax;

    /**
     * Coupling between object classes
     */
    private double cboMin;
    private double cboMax;

    /**
     * Class interface size
     */
    private double cisMin;
    private double cisMax;

    public void setLoc(double locMin, double locMax) {
        if (locMin < 0) {
            this.locMin = LOC.getDefaultThresholdMin();
        } else {
            this.locMin = locMin;
        }
        if (locMax < 0) {
            this.locMax = LOC.getDefaultThresholdMin();
        } else {
            this.locMax = locMax;
        }
    }

    public void setWmc(double wmcMin, double wmcMax) {
        if (wmcMin < 0) {
            this.wmcMin = WMC.getDefaultThresholdMin();
        } else {
            this.wmcMin = wmcMin;
        }
        if (wmcMax < 0) {
            this.wmcMax = WMC.getDefaultThresholdMax();
        } else {
            this.wmcMax = wmcMax;
        }
    }

    public void setNoc(double nocMin, double nocMax) {
        if (nocMin < 0) {
            this.nocMin = NOC.getDefaultThresholdMin();
        } else {
            this.nocMin = nocMin;
        }
        if (nocMax < 0) {
            this.nocMax = NOC.getDefaultThresholdMax();
        } else {
            this.nocMax = nocMax;
        }
    }

    public void setRfc(double rfcMin, double rfcMax) {
        if (rfcMin < 0) {
            this.rfcMin = RFC.getDefaultThresholdMin();
        } else {
            this.rfcMin = rfcMin;
        }
        if (rfcMax < 0) {
            this.rfcMax = RFC.getDefaultThresholdMax();
        } else {
            this.rfcMax = rfcMax;
        }
    }

    public void setCe(double ceMin, double ceMax) {
        if (ceMin < 0) {
            this.ceMin = CE.getDefaultThresholdMin();
        } else {
            this.ceMin = ceMin;
        }
        if (ceMax < 0) {
            this.ceMax = CE.getDefaultThresholdMax();
        } else {
            this.ceMax = ceMax;
        }
    }

    public void setCa(double caMin, double caMax) {
        if (caMin < 0) {
            this.caMin = CA.getDefaultThresholdMin();
        } else {
            this.caMin = caMin;
        }
        if (caMax < 0) {
            this.caMax = CA.getDefaultThresholdMax();
        } else {
            this.caMax = caMax;
        }
    }

    public void setDit(double ditMin, double ditMax) {
        if (ditMin < 0) {
            this.ditMin = DIT.getDefaultThresholdMin();
        } else {
            this.ditMin = ditMin;
        }
        if (ditMax < 0) {
            this.ditMax = DIT.getDefaultThresholdMax();
        } else {
            this.ditMax = ditMax;
        }
    }

    public void setLcom(double lcomMin, double lcomMax) {
        if (lcomMin < 0) {
            this.lcomMin = LCOM.getDefaultThresholdMin();
        } else {
            this.lcomMin = lcomMin;
        }
        if (lcomMax < 0) {
            this.lcomMax = LCOM.getDefaultThresholdMax();
        } else {
            this.lcomMax = lcomMax;
        }
    }

    public void setLcom3(double lcom3Min, double lcom3Max) {
        if (lcom3Min < 0) {
            this.lcom3Min = LCOM3.getDefaultThresholdMin();
        } else {
            this.lcom3Min = lcom3Min;
        }
        if (lcom3Max < 0) {
            this.lcom3Max = LCOM3.getDefaultThresholdMax();
        } else {
            this.lcom3Max = lcom3Max;
        }
    }

    public void setNpm(double npmMin, double npmMax) {
        if (npmMin < 0) {
            this.npmMin = NPM.getDefaultThresholdMin();
        } else {
            this.npmMin = npmMin;
        }
        if (npmMax < 0) {
            this.npmMax = NPM.getDefaultThresholdMax();
        } else {
            this.npmMax = npmMax;
        }
    }

    public void setCc(double ccMin, double ccMax) {
        if (ccMin < 0) {
            this.ccMin = CC.getDefaultThresholdMin();
        } else {
            this.ccMin = ccMin;
        }
        if (ccMax < 0) {
            this.ccMax = CC.getDefaultThresholdMax();
        } else {
            this.ccMax = ccMax;
        }
    }

    public void setDam(double damMin, double damMax) {
        if (damMin < 0) {
            this.damMin = DAM.getDefaultThresholdMin();
        } else {
            this.damMin = damMin;
        }
        if (damMax < 0) {
            this.damMax = DAM.getDefaultThresholdMax();
        } else {
            this.damMax = damMax;
        }
    }

    public void setMoa(double moaMin, double moaMax) {
        if (moaMin < 0) {
            this.moaMin = MOA.getDefaultThresholdMin();
        } else {
            this.moaMin = moaMin;
        }
        if (moaMax < 0) {
            this.moaMax = MOA.getDefaultThresholdMax();
        } else {
            this.moaMax = moaMax;
        }
    }

    public void setMfa(double mfaMin, double mfaMax) {
        if (mfaMin < 0) {
            this.mfaMin = MFA.getDefaultThresholdMin();
        } else {
            this.mfaMin = mfaMin;
        }
        if (mfaMax < 0) {
            this.mfaMax = MFA.getDefaultThresholdMax();
        } else {
            this.mfaMax = mfaMax;
        }
    }

    public void setCam(double camMin, double camMax) {
        if (camMin < 0) {
            this.camMin = CAM.getDefaultThresholdMin();
        } else {
            this.camMin = camMin;
        }
        if (camMax < 0) {
            this.camMax = CAM.getDefaultThresholdMax();
        } else {
            this.camMax = camMax;
        }
    }

    public void setIc(double icMin, double icMax) {
        if (icMin < 0) {
            this.icMin = IC.getDefaultThresholdMin();
        } else {
            this.icMin = icMin;
        }
        if (icMax < 0) {
            this.icMax = IC.getDefaultThresholdMax();
        } else {
            this.icMax = icMax;
        }
    }

    public void setCbm(double cbmMin, double cbmMax) {
        if (cbmMin < 0) {
            this.cbmMin = CBM.getDefaultThresholdMin();
        } else {
            this.cbmMin = cbmMin;
        }
        if (cbmMax < 0) {
            this.cbmMax = CBM.getDefaultThresholdMax();
        } else {
            this.cbmMax = cbmMax;
        }
    }

    public void setAmc(double amcMin, double amcMax) {
        if (amcMin < 0) {
            this.amcMin = AMC.getDefaultThresholdMin();
        } else {
            this.amcMin = amcMin;
        }
        if (amcMax < 0) {
            this.amcMax = AMC.getDefaultThresholdMax();
        } else {
            this.amcMax = amcMax;
        }
    }

    public void setCbo(double cboMin, double cboMax) {
        if (cboMin < 0) {
            this.cboMin = CBO.getDefaultThresholdMin();
        } else {
            this.cboMin = cboMin;
        }
        if (cboMax < 0) {
            this.cboMax = CBO.getDefaultThresholdMax();
        } else {
            this.cboMax = cboMax;
        }
    }

    public void setCis(double cisMin, double cisMax) {
        if (cisMin < 0) {
            this.cisMin = CIS.getDefaultThresholdMin();
        } else {
            this.cisMin = cisMin;
        }
        if (cisMax < 0) {
            this.cisMax = CIS.getDefaultThresholdMax();
        } else {
            this.cisMax = cisMax;
        }    }
}

package eu.qped.java.checkers.design.configuration;

import eu.qped.framework.qf.QfObjectBase;
import eu.qped.java.checkers.design.DesignChecker;
import eu.qped.java.checkers.design.DesignSuggestion;
import eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.Metric;
import lombok.*;

import java.util.HashMap;

/**
 * Class modeling design settings for {@link DesignChecker}.
 * The fields are modeling the threshold for the corresponding metric, custom suggestions and other settings for the checker.
 *
 * @author Jannik Seus
 */
@Getter
@Setter
@Builder
public class DesignSettings extends QfObjectBase {

    @Getter(AccessLevel.NONE)
    private boolean includeCallsToJdk;

    @Getter(AccessLevel.NONE)
    private boolean includeOnlyPublicClasses;

    private HashMap<Metric, DesignSuggestion> customSuggestions;

    /**
     * Average method Complexity
     */
    private MetricThreshold amcThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion amcSuggestion;

    /**
     * Coupled classes: classes being used by this class (afferent)
     */
    private MetricThreshold caThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion caSuggestion;


    /**
     * Cohesion Among methods of Class
     */
    private MetricThreshold camThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion camSuggestion;

    /**
     * Coupling Between methods
     */
    private MetricThreshold cbmThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion cbmSuggestion;

    /**
     * Coupling between object classes
     */
    private MetricThreshold cboThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion cboSuggestion;

    /**
     * Signatures of methods and values of McCabe Cyclomatic Complexity
     */
    private MetricThreshold ccThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion ccSuggestion;

    /**
     * Coupled classes: classes that use this class (efferent)
     */
    private MetricThreshold ceThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion ceSuggestion;

    /**
     * Data Access metric
     */
    private MetricThreshold damThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion damSuggestion;

    /**
     * Depth of inheritance tree
     */
    private MetricThreshold ditThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion ditSuggestion;


    /**
     * Inheritance Coupling
     */
    private MetricThreshold icThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion icSuggestion;

    /**
     * Lack of cohesion in methods
     */
    private MetricThreshold lcomThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion lcomSuggestion;

    /**
     * Lack of cohesion in methods - Henderson-Sellers definition
     */
    private MetricThreshold lcom3Threshold;
    private eu.qped.java.checkers.design.DesignSuggestion lcom3Suggestion;

    /**
     * Line of codes per class (minimum and maximum thresholds)
     */
    private MetricThreshold locThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion locSuggestion;

    /**
     * measure of Aggregation
     */
    private MetricThreshold moaThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion moaSuggestion;

    /**
     * measure of Functional Abstraction
     */
    private MetricThreshold mfaThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion mfaSuggestion;

    /**
     * Number of children
     */
    private MetricThreshold nocThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion nocSuggestion;

    /**
     * Number of public methods
     */
    private MetricThreshold npmThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion npmSuggestion;


    /**
     * Response for a Class
     */
    private MetricThreshold rfcThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion rfcSuggestion;

    /**
     * Weighted methods per class
     */
    private MetricThreshold wmcThreshold;
    private eu.qped.java.checkers.design.DesignSuggestion wmcSuggestion;


    public boolean areCallsToToJdkIncluded() {
        return includeCallsToJdk;
    }

    public void includeCallsToJdk(boolean includeCallsToJdk) {
        this.includeCallsToJdk = includeCallsToJdk;
    }

    public boolean areOnlyPublicClassesIncluded() {
        return includeOnlyPublicClasses;
    }

    public void includeOnlyPublicClasses(boolean includeNonPublicClasses) {
        this.includeOnlyPublicClasses = includeNonPublicClasses;
    }
}

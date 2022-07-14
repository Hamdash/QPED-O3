package eu.qped.java.checkers.metrics.configuration;

import eu.qped.framework.qf.QfObjectBase;
import eu.qped.java.checkers.metrics.MetricsChecker;
import eu.qped.java.checkers.metrics.MetricsCheckerSuggestion;
import eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric;
import lombok.*;

import java.util.HashMap;

/**
 * Class modeling design settings for {@link MetricsChecker}.
 * The fields are modeling the threshold for the corresponding metric, custom suggestions and other settings for the checker.
 *
 * @author Jannik Seus
 */
@Getter
@Setter
@Builder
public class MetricsCheckerSettings extends QfObjectBase {

    @Getter(AccessLevel.NONE)
    private boolean includeCallsToJdk;

    @Getter(AccessLevel.NONE)
    private boolean includeOnlyPublicClasses;

    private HashMap<Metric, MetricsCheckerSuggestion> customSuggestions;

    /**
     * Average method Complexity
     */
    private MetricThreshold amcThreshold;
    private MetricsCheckerSuggestion amcSuggestion;

    /**
     * Coupled classes: classes being used by this class (afferent)
     */
    private MetricThreshold caThreshold;
    private MetricsCheckerSuggestion caSuggestion;


    /**
     * Cohesion Among methods of Class
     */
    private MetricThreshold camThreshold;
    private MetricsCheckerSuggestion camSuggestion;

    /**
     * Coupling Between methods
     */
    private MetricThreshold cbmThreshold;
    private MetricsCheckerSuggestion cbmSuggestion;

    /**
     * Coupling between object classes
     */
    private MetricThreshold cboThreshold;
    private MetricsCheckerSuggestion cboSuggestion;

    /**
     * Signatures of methods and values of McCabe Cyclomatic Complexity
     */
    private MetricThreshold ccThreshold;
    private MetricsCheckerSuggestion ccSuggestion;

    /**
     * Coupled classes: classes that use this class (efferent)
     */
    private MetricThreshold ceThreshold;
    private MetricsCheckerSuggestion ceSuggestion;

    /**
     * Data Access metric
     */
    private MetricThreshold damThreshold;
    private MetricsCheckerSuggestion damSuggestion;

    /**
     * Depth of inheritance tree
     */
    private MetricThreshold ditThreshold;
    private MetricsCheckerSuggestion ditSuggestion;


    /**
     * Inheritance Coupling
     */
    private MetricThreshold icThreshold;
    private MetricsCheckerSuggestion icSuggestion;

    /**
     * Lack of cohesion in methods
     */
    private MetricThreshold lcomThreshold;
    private MetricsCheckerSuggestion lcomSuggestion;

    /**
     * Lack of cohesion in methods - Henderson-Sellers definition
     */
    private MetricThreshold lcom3Threshold;
    private MetricsCheckerSuggestion lcom3Suggestion;

    /**
     * Line of codes per class (minimum and maximum thresholds)
     */
    private MetricThreshold locThreshold;
    private MetricsCheckerSuggestion locSuggestion;

    /**
     * measure of Aggregation
     */
    private MetricThreshold moaThreshold;
    private MetricsCheckerSuggestion moaSuggestion;

    /**
     * measure of Functional Abstraction
     */
    private MetricThreshold mfaThreshold;
    private MetricsCheckerSuggestion mfaSuggestion;

    /**
     * Number of children
     */
    private MetricThreshold nocThreshold;
    private MetricsCheckerSuggestion nocSuggestion;

    /**
     * Number of public methods
     */
    private MetricThreshold npmThreshold;
    private MetricsCheckerSuggestion npmSuggestion;


    /**
     * Response for a Class
     */
    private MetricThreshold rfcThreshold;
    private MetricsCheckerSuggestion rfcSuggestion;

    /**
     * Weighted methods per class
     */
    private MetricThreshold wmcThreshold;
    private MetricsCheckerSuggestion wmcSuggestion;


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

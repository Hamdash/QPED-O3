package eu.qped.java.checkers.design.ckjm;

import eu.qped.java.checkers.design.data.DesignCheckEntry;
import eu.qped.java.checkers.design.data.DesignCheckMessage;
import eu.qped.java.checkers.design.data.DesignCheckReport;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.qped.java.checkers.design.ckjm.SaveMapResults.Metric.*;

/**
 * Class is used to handle the output generated by the checker.
 * The output is saved in a nested map {@link #outputMetrics}.
 *
 * @author Jannik Seus
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SaveMapResults implements CkjmOutputHandler {

    @Singular
    private List<DesignCheckEntry> outputMetrics;


    public SaveMapResults() {
        this.outputMetrics = new ArrayList<>();
    }

    @Override
    public void handleClass(String className, ClassMetrics c) {

        List<DesignCheckMessage> metricsForClass = new ArrayList<>();

        metricsForClass.add(new DesignCheckMessage(WMC, c.getWmc()));
        metricsForClass.add(new DesignCheckMessage(DIT, c.getDit()));
        metricsForClass.add(new DesignCheckMessage(NOC, c.getNoc()));
        metricsForClass.add(new DesignCheckMessage(CBO, c.getCbo()));
        metricsForClass.add(new DesignCheckMessage(RFC, c.getRfc()));
        metricsForClass.add(new DesignCheckMessage(LCOM, c.getLcom()));
        metricsForClass.add(new DesignCheckMessage(CA, c.getCa()));
        metricsForClass.add(new DesignCheckMessage(CE, c.getCe()));
        metricsForClass.add(new DesignCheckMessage(NPM, c.getNpm()));
        metricsForClass.add(new DesignCheckMessage(LCOM3, c.getLcom3()));
        metricsForClass.add(new DesignCheckMessage(LOC, c.getLoc()));
        metricsForClass.add(new DesignCheckMessage(DAM, c.getDam()));
        metricsForClass.add(new DesignCheckMessage(MOA, c.getMoa()));
        metricsForClass.add(new DesignCheckMessage(MFA, c.getMfa()));
        metricsForClass.add(new DesignCheckMessage(CAM, c.getCam()));
        metricsForClass.add(new DesignCheckMessage(IC, c.getIc()));
        metricsForClass.add(new DesignCheckMessage(CBM, c.getCbm()));
        metricsForClass.add(new DesignCheckMessage(AMC, c.getAmc()));

        outputMetrics.add(new DesignCheckEntry(className, metricsForClass));
    }

    /**
     * Metrics enum representing all possible class metrics for the design checker.
     * <p>
     * defaultValue contains an initializing value for a specific metric (before setting the value)
     * defaultThreshold[0] contains the default minimum threshold
     * defaultThreshold[1] contains the default maximum threshold
     *
     * @author Jannik Seus
     */
    public enum Metric {

        AMC("Average Method Complexity", 0d, new double[]{}),
        CAM("Cohesion Among Methods of Class", 0d, new double[]{}),
        CA("Afferent coupling", 0d, new double[]{}),
        CBM("Coupling Between Methods", 0d, new double[]{}),
        CBO("Coupling between object classes", 0d, new double[]{}),
        CC("McCabe's Cyclomatic Complexity", 1d, new double[]{}),
        CE("Efferent coupling", 0d, new double[]{}),
        CIS("Class Interface Size", 0d, new double[]{}),
        DAM("Data Access Metric", 0d, new double[]{}),
        DIT("Depth of inheritance tree", 0d, new double[]{}),
        IC("Inheritance Coupling", 0d, new double[]{}),
        LCOM("Lack of cohesion in methods", 0d, new double[]{}),
        LCOM3("Lack of cohesion in methods Henderson-Sellers version", 0d, new double[]{}),
        LOC("Lines of Code", 0d, new double[]{}),
        MOA("Measure of Aggregation", 0d, new double[]{}),
        MFA("Measure of Functional Abstraction", 0d, new double[]{}),
        NOC("Number of Children", 0d, new double[]{}),
        NPM("Number of Public Methods for a class", 0d, new double[]{}),
        RFC("Response for a Class", 0d, new double[]{}),
        WMC("Weighted methods per class", 0d, new double[]{});

        /**
         * Represents a metric's description.
         */
        private final String description;

        /**
         * A metric's value to begin with. This is not representing a field to store the data.
         */
        private final double initialValue;

        /**
         * TODO look up thresholds in paper "Thresholds for object-oriented measures"
         * Represents default thresholds (min, max) of given metrics.
         */
        private final double[] defaultThresholds;

        Metric(String description, double initialValue, double[] defaultThresholds) {
            this.description = description;
            this.initialValue = initialValue;
            this.defaultThresholds = defaultThresholds;
        }

        public String getDescription() {
            return description;
        }

        public double getInitialValue() {
            return initialValue;
        }

        public double[] getDefaultThresholds() {
            return defaultThresholds;
        }

        public double getDefaultThresholdMin() {
            return defaultThresholds[0];
        }

        public double getDefaultThresholdMax() {
            return defaultThresholds[1];
        }
    }

}

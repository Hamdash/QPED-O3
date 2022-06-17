package eu.qped.java.checkers.design;

import eu.qped.framework.Feedback;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jannik Seus
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
public class DesignFeedback extends Feedback {
    private String metric;
    private String thresholdReached;
    private String errorSource; //code snippet
    private String errorLine; // line of code if possible
    private String solutionExample; //TODO is this even necessary?

    @Override
    public String toString() {
        return "DesignFeedback{" +
                "metric='" + metric + '\'' +
                "body='" + super.getBody() + '\'' +
                ", thresholdReached='" + thresholdReached + '\'' +
                ", errorSource='" + errorSource + '\'' +
                ", errorLine='" + errorLine + '\'' +
                ", solutionExample='" + solutionExample + '\'' +
                '}';
    }
}

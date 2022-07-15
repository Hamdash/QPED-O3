package eu.qped.java.checkers.metrics.data.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetricsCheckerSuggestion {

    private String suggestionLowerBoundExceeded;
    private String suggestionUpperBoundExceeded;
}
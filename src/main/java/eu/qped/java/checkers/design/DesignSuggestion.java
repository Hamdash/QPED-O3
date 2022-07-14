package eu.qped.java.checkers.design;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DesignSuggestion {

    private String suggestionLowerBoundExceeded;
    private String suggestionUpperBoundExceeded;
}
package eu.qped.java.checkers.style.feedback;

import eu.qped.framework.feedback.RelatedLocation;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackDirectoryProvider;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackParser;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.utils.FileExtensions;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultStyleFeedbackParser {

    DefaultFeedbackParser defaultFeedbackParser;

    public List<DefaultStyleFeedback> parse(String language) {
        var dirPath = DefaultFeedbackDirectoryProvider.provideDefaultFeedbackDirectory(StyleChecker.class);
        if (defaultFeedbackParser == null) {
            defaultFeedbackParser = new DefaultFeedbackParser();
        }
        var allDefaultFeedbacks = defaultFeedbackParser.parse(dirPath, language + FileExtensions.JSON);
        return allDefaultFeedbacks.stream().map(defaultFeedback -> DefaultStyleFeedback.builder()
                .defaultFeedback(defaultFeedback)
                .relatedLocation(RelatedLocation.builder().build())
                .build()
        ).collect(Collectors.toList());
    }
}

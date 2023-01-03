package eu.qped.framework.feedback;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackManager {
    private List<Feedback> feedbacks;
    private boolean isFormatted = false;

    public List<Feedback> formatFeedbacks() {
        if(feedbacks == null) feedbacks = Collections.emptyList();
        feedbacks.forEach(Feedback::format);
        isFormatted = true;
        return feedbacks;
    }

    public List<String> buildFeedbackInTemplate(@NonNull String language) {
        if(feedbacks == null) feedbacks = Collections.emptyList();
        if(! isFormatted ) feedbacks = formatFeedbacks();
        return feedbacks.stream()
                .map(feedback -> feedback.buildFeedbackInTemplate(language))
                .collect(Collectors.toList())
        ;
    }




}

package eu.qped.java.checkers.style.feedback;


import eu.qped.framework.feedback.Feedback;
import eu.qped.framework.feedback.Type;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackMapper;
import eu.qped.java.checkers.style.StyleChecker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultStyleFeedbackMapper {

    private DefaultFeedbackMapper defaultFeedbackMapper;

    public List<Feedback> map(List<DefaultStyleFeedback> feedbacks) {
        if (defaultFeedbackMapper == null) defaultFeedbackMapper = new DefaultFeedbackMapper();
        return feedbacks.stream().map(this::map).collect(Collectors.toList());
    }

    private Feedback map(DefaultStyleFeedback defaultStyleFeedback) {
        var feedback = defaultFeedbackMapper.mapDefaultFeedbackToFeedback(defaultStyleFeedback.getDefaultFeedback(), StyleChecker.class, Type.IMPROVEMENT);
        feedback.setRelatedLocation(defaultStyleFeedback.getRelatedLocation());
        return feedback;
    }
}

package eu.qped.java.checkers.solutionapproach.feedback;

import eu.qped.framework.feedback.Feedback;
import eu.qped.framework.feedback.Type;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackMapper;
import eu.qped.java.checkers.solutionapproach.SolutionApproachChecker;
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
public class DefaultSolutionApproachFeedbackMapper {

    private DefaultFeedbackMapper defaultFeedbackMapper;

    public List<Feedback> map(List<DefaultSolutionApproachFeedback> feedbacks) {
        if (defaultFeedbackMapper == null) defaultFeedbackMapper = new DefaultFeedbackMapper();
        return feedbacks.stream().map(this::map).collect(Collectors.toList());
    }

    private Feedback map(DefaultSolutionApproachFeedback defaultSolutionApproachFeedback) {
        var feedback = defaultFeedbackMapper.mapDefaultFeedbackToFeedback(defaultSolutionApproachFeedback.getDefaultFeedback(), SolutionApproachChecker.class, Type.CORRECTION);
        feedback.setRelatedLocation(defaultSolutionApproachFeedback.getRelatedLocation());
        return feedback;
    }
}

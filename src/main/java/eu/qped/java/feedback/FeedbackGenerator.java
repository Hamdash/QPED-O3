package eu.qped.java.feedback;

import eu.qped.framework.Feedback;

import java.util.List;

public interface FeedbackGenerator<T> {
    public List<Feedback> generateFeedbacks(List<T> errors);
}

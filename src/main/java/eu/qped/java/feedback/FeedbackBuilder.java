package eu.qped.java.feedback;

public abstract class FeedbackBuilder {
    public String buildFeedback(String error, String sourceExample) {
        return buildHeader() +
                "\n\n" +
                buildBody(error) +
                "\n\n" +
                buildSolutionExample();
    }


    public abstract String buildHeader();

    public abstract String buildSolutionExample();

    public abstract String buildBody(String error);

    public abstract String buildErrorSourceCode(String error);

}

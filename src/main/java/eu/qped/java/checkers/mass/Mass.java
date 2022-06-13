package eu.qped.java.checkers.mass;

import eu.qped.framework.Checker;
import eu.qped.framework.QfProperty;
import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.design.DesignChecker;
import eu.qped.java.checkers.semantics.SemanticChecker;
import eu.qped.java.checkers.semantics.SemanticConfigurator;
import eu.qped.java.checkers.semantics.SemanticFeedback;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.checkers.style.StyleConfigurator;
import eu.qped.java.checkers.style.StyleFeedback;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.feedback.syntax.SyntaxFeedback;

import java.util.List;


public class Mass implements Checker {

    @QfProperty
    private QFMainSettings mainSettings;

    @QfProperty
    private QFStyleSettings styleSettings;

    @QfProperty
    private QFSemSettings semSettings;

    @QfProperty
    private QFDesignSettings designSettings;

    private final static String NEW_LINE = "\n" + "\n";

    @Override
    public void check(QfObject qfObject) throws Exception {


        StyleConfigurator styleConfigurator = StyleConfigurator.createStyleConfigurator(this.styleSettings);

        StyleChecker styleChecker = new StyleChecker(styleConfigurator);


        MainSettings mainSettings = new MainSettings(this.mainSettings);

        SemanticConfigurator semanticConfigurator = SemanticConfigurator.createSemanticConfigurator(semSettings);


        SemanticChecker semanticChecker = SemanticChecker.createSemanticMassChecker(semanticConfigurator);
        SyntaxChecker syntaxChecker = SyntaxChecker.builder().stringAnswer(qfObject.getAnswer()).build();
        DesignChecker designChecker = DesignChecker.builder().answer(qfObject.getAnswer()).build(); //TODO is this correct?
        MassExecutor massExecutor = new MassExecutor(styleChecker, semanticChecker, syntaxChecker, designChecker, mainSettings);

        massExecutor.execute();

        /*
         feedbacks
         */
        List<StyleFeedback> styleFeedbacks;
        styleFeedbacks = massExecutor.getStyleFeedbacks();

        List<SyntaxFeedback> syntaxFeedbacks;
        syntaxFeedbacks = massExecutor.getSyntaxFeedbacks();

        List<SemanticFeedback> semanticFeedbacks;
        semanticFeedbacks = massExecutor.getSemanticFeedbacks();


        String[] result = new String[styleFeedbacks.size() + semanticFeedbacks.size() + syntaxFeedbacks.size() + 100];

        int i = 0;

        for (StyleFeedback styleFeedback : styleFeedbacks) {
            result[i] = "style Feedback";
            result[i + 1] = styleFeedback.getDesc()
                    + NEW_LINE
                    + styleFeedback.getBody()
                    + NEW_LINE
                    + styleFeedback.getLine()
                    + NEW_LINE
                    + styleFeedback.getExample()
                    + NEW_LINE
                    + "------------------------------------------------------------------------------";
            i = i + 2;
        }

        for (SemanticFeedback semanticFeedback : semanticFeedbacks) {
            result[i] = "semantic Feedback";
            result[i + 1] = semanticFeedback.getBody() + NEW_LINE
                    + "--------------------------------------------------";
            i = i + 2;
        }

        for (SyntaxFeedback syntax : syntaxFeedbacks) {
            result[i + 1] = ""
                    + syntax.toString()
                    + NEW_LINE
                    + "--------------------------------------------------"
            ;
            i = i + 2;
        }


        qfObject.setFeedback(result);
    }

}
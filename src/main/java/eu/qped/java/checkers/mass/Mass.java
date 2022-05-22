package eu.qped.java.checkers.mass;

import eu.qped.framework.CheckLevel;
import eu.qped.framework.Checker;
import eu.qped.framework.QfProperty;
import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.semantics.SemanticChecker;
import eu.qped.java.checkers.semantics.SemanticConfigurator;
import eu.qped.java.checkers.semantics.SemanticFeedback;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.checkers.style.StyleConfigurator;
import eu.qped.java.checkers.style.StyleFeedback;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.feedback.syntaxLagacy.SyntaxFeedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Mass implements Checker {

    @QfProperty
    private QFMainSettings mainSettings;

    @QfProperty
    private QFStyleSettings styleSettings;

    @QfProperty
    private QFSemSettings semSettings;

    private final static String NEW_LINE = "\n" + "\n";

    @Override
    public void check(QfObject qfObject) throws Exception {


        Map<String, String> mainSettingMap = new HashMap<>();
        mainSettingMap.put("semanticNeeded", mainSettings.getSemanticNeeded());
        mainSettingMap.put("syntaxLevel", mainSettings.getSyntaxLevel());
        mainSettingMap.put("preferredLanguage", mainSettings.getPreferredLanguage());
        mainSettingMap.put("styleNeeded", mainSettings.getStyleNeeded());


        MainSettings mainSettingsConfiguratorConf = new MainSettings(mainSettingMap);


        StyleConfigurator styleConfigurator = StyleConfigurator.createStyleConfigurator(this.styleSettings);

        StyleChecker styleChecker = new StyleChecker(styleConfigurator);




        SemanticConfigurator semanticConfigurator = SemanticConfigurator.createSemanticConfigurator(semSettings);
        SemanticChecker semanticChecker = SemanticChecker.createSemanticMassChecker(semanticConfigurator);


        SyntaxChecker syntaxChecker = SyntaxChecker.builder().stringAnswer(qfObject.getAnswer()).build();

        MassExecutor massExecutor = new MassExecutor(styleChecker, semanticChecker, syntaxChecker, mainSettingsConfiguratorConf);

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
                    + syntax.getFeedbackContent()
                    + NEW_LINE
                    + "At Line: " +  syntax.getSyntaxError().getLine()
                    + NEW_LINE
                    + "--------------------------------------------------"
            ;
            i = i + 2;
        }

        qfObject.setFeedback(result);
    }

}
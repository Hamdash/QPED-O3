package eu.qped.java.checkers.mass;

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
import eu.qped.java.feedback.syntax.SyntaxFeedbackNew;
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

        Map<String, String> styleSettings = new HashMap<>();
        Map<String, String> mainSettings = new HashMap<>();


        /*
        main settings
         */
        if (this.mainSettings != null) {
            mainSettings.put("syntaxLevel", this.mainSettings.getSyntaxLevel());
            mainSettings.put("preferredLanguage", this.mainSettings.getPreferredLanguage());
            mainSettings.put("styleNeeded", this.mainSettings.getStyleNeeded());
            mainSettings.put("semanticNeeded", this.mainSettings.getSemanticNeeded());
        }
        
        /*
        Style Configs
         */
        if (this.styleSettings != null) {
            styleSettings.put("mainLevel", this.styleSettings.getMainLevel());
            styleSettings.put("maxClassLength", this.styleSettings.getClassLength());
            styleSettings.put("maxMethodLength", this.styleSettings.getMethodLength());
            styleSettings.put("maxFieldsCount", this.styleSettings.getFieldsCount());
            styleSettings.put("maxCycloComplexity", this.styleSettings.getCycloComplexity());
            styleSettings.put("varNamesRegEx", this.styleSettings.getVarName());
            styleSettings.put("methodNamesRegEx", this.styleSettings.getMethodName());
            styleSettings.put("classNameRegEx", this.styleSettings.getClassName());
            styleSettings.put("basisLevel", this.styleSettings.getBasisLevel());
            styleSettings.put("namesLevel", this.styleSettings.getNamesLevel());
            styleSettings.put("compLevel", this.styleSettings.getCompLevel());
        }

        StyleConfigurator styleConfigurator = StyleConfigurator.createStyleConfigurator(this.styleSettings);

        StyleChecker styleChecker = new StyleChecker(styleConfigurator);


        Map<String, String> semanticSettings = new HashMap<>();

        /*
        Semantic Configs
         */
        if (this.semSettings != null) {
            semanticSettings.put("methodName", this.semSettings.getMethodName());
            semanticSettings.put("recursionAllowed", this.semSettings.getRecursionAllowed());
            semanticSettings.put("whileLoop", this.semSettings.getWhileLoop());
            semanticSettings.put("forLoop", this.semSettings.getForLoop());
            semanticSettings.put("forEachLoop", this.semSettings.getForEachLoop());
            semanticSettings.put("ifElseStmt", this.semSettings.getIfElseStmt());
            semanticSettings.put("doWhileLoop", this.semSettings.getDoWhileLoop());
            semanticSettings.put("returnType", this.semSettings.getReturnType());
        }

        MainSettings mainSettingsConfiguratorConf = new MainSettings(mainSettings);
        SemanticConfigurator semanticConfigurator = SemanticConfigurator.createSemanticConfigurator(new QFSemSettings());


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
                    + "--------------------------------------------------"
            ;
            i = i + 2;
        }

        for (SyntaxFeedbackNew syntax : massExecutor.getSyntaxFeedbackNews()) {
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

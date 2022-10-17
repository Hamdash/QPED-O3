package eu.qped.java.checkers.mass;

import eu.qped.framework.CheckLevel;
import eu.qped.framework.Feedback;
import eu.qped.framework.Translator;
import eu.qped.java.checkers.classdesign.ClassChecker;
import eu.qped.java.checkers.classdesign.feedback.ClassFeedback;
import eu.qped.java.checkers.coverage.CoverageChecker;
import eu.qped.java.checkers.metrics.MetricsChecker;
import eu.qped.java.checkers.metrics.data.feedback.MetricsFeedback;
import eu.qped.java.checkers.semantics.SemanticChecker;
import eu.qped.java.checkers.semantics.SemanticFeedback;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.checkers.style.StyleFeedback;
import eu.qped.java.checkers.syntax.SyntaxCheckReport;
import eu.qped.java.checkers.syntax.SyntaxError;
import eu.qped.java.checkers.syntax.SyntaxErrorAnalyser;
import eu.qped.java.checkers.syntax.feedback.AbstractSyntaxFeedbackGenerator;
import eu.qped.java.checkers.syntax.feedback.SyntaxFeedback;
import eu.qped.java.checkers.syntax.feedback.SyntaxFeedbackGenerator;
import eu.qped.java.utils.SupportedLanguages;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Executor class, execute all components of the System to analyze the code
 *
 * @author Basel Alaktaa & Mayar Hamdash
 * @version 1.0
 * @since 19.08.2021
 */
@Getter
@Setter
public class MassExecutor {

    private final MainSettings mainSettings;


    private List<StyleFeedback> styleFeedbacks;
    private List<SemanticFeedback> semanticFeedbacks;
    private List<SyntaxFeedback> syntaxFeedbacks;
    private List<ClassFeedback> classFeedbacks;
    private List<MetricsFeedback> metricsFeedbacks;
    private String[] coverageFeedbacks;

    private List<SyntaxError> syntaxErrors;

    private final StyleChecker styleChecker;
    private final SemanticChecker semanticChecker;
    private final SyntaxErrorAnalyser syntaxErrorAnalyser;
    private final ClassChecker classChecker;
    private MetricsChecker metricsChecker;
    private final CoverageChecker coverageChecker;

    /**
     * To create an Object use the factory Class @MassExecutorFactory
     *
     * @param styleChecker        style checker component
     * @param semanticChecker     semantic checker component
     * @param syntaxErrorAnalyser syntax checker component
     * @param metricsChecker      metrics checker component
     * @param mainSettings        settings
     */

    public MassExecutor(final StyleChecker styleChecker, final SemanticChecker semanticChecker,
                        final SyntaxErrorAnalyser syntaxErrorAnalyser, final MetricsChecker metricsChecker,
                        final ClassChecker classChecker,
                        final CoverageChecker coverageChecker,
                        final MainSettings mainSettings
    ) {

        this.styleChecker = styleChecker;
        this.semanticChecker = semanticChecker;
        this.syntaxErrorAnalyser = syntaxErrorAnalyser;
        this.metricsChecker = metricsChecker;
        this.classChecker = classChecker;
        this.coverageChecker = coverageChecker;
        this.mainSettings = mainSettings;
        this.coverageFeedbacks = new String[]{};
    }

    /**
     * execute the Mass System
     */
    public void execute() {
        init();

        boolean styleNeeded = mainSettings.isStyleNeeded();
        boolean semanticNeeded = mainSettings.isSemanticNeeded();
        boolean metricsNeeded = mainSettings.isMetricsNeeded();
        boolean classNeeded = mainSettings.isClassNeeded();
        boolean coverageNeeded = mainSettings.isCoverageNeeded();


        SyntaxCheckReport syntaxCheckReport = syntaxErrorAnalyser.check();

        if (syntaxCheckReport.isCompilable()) {
            if (styleNeeded) {
                styleChecker.setTargetPath(syntaxCheckReport.getPath());
                styleChecker.check();
                styleFeedbacks = styleChecker.getStyleFeedbacks();
            }
            if (semanticNeeded) {
                semanticChecker.setTargetProjectPath(syntaxCheckReport.getPath());
                semanticChecker.check();
                semanticFeedbacks = semanticChecker.getFeedbacks();
            }
            if (metricsNeeded) {
                syntaxErrorAnalyser.setClassFilesDestination("");
                metricsChecker.check();
                metricsFeedbacks = metricsChecker.getMetricsFeedbacks();
            }
            if (classNeeded) {
                try {
                    classChecker.setTargetPath(syntaxCheckReport.getPath());
                    classChecker.check(null);
                    classFeedbacks = classChecker.getClassFeedbacks();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (coverageNeeded)
                coverageFeedbacks = coverageChecker.check();

        } else if (coverageNeeded) {
            // Found no other solution:
            // The problem is if the student answer needs a klass from a teacher to compile
            // the syntaxChecker always fails.
            coverageFeedbacks = coverageChecker.check();
        } else {
            syntaxErrors = syntaxCheckReport.getSyntaxErrors();
            AbstractSyntaxFeedbackGenerator syntaxFeedbackGenerator = SyntaxFeedbackGenerator.builder().build();
            syntaxFeedbacks = syntaxFeedbackGenerator.generateFeedbacks(syntaxErrors);
        }

        // translate Feedback body if needed
        if (!mainSettings.getPreferredLanguage().equals(SupportedLanguages.ENGLISH)) {
            translate(styleNeeded, semanticNeeded, metricsNeeded);
        }
    }

    private void init() {
        syntaxFeedbacks = new ArrayList<>();
        styleFeedbacks = new ArrayList<>();
        semanticFeedbacks = new ArrayList<>();
        metricsFeedbacks = new ArrayList<>();
        classFeedbacks = new ArrayList<>();
        syntaxErrors = new ArrayList<>();

    }


    private void translate(boolean styleNeeded, boolean semanticNeeded, boolean metricsNeeded) {
        String prefLanguage = mainSettings.getPreferredLanguage();
        Translator translator = new Translator();

        //List is Empty when the syntax is correct
        for (SyntaxFeedback feedback : syntaxFeedbacks) {
            translator.translateBody(prefLanguage, feedback);
        }
        if (semanticNeeded) {
            for (Feedback feedback : semanticFeedbacks) {
                translator.translateBody(prefLanguage, feedback);
            }
        }
        if (styleNeeded) {
            for (StyleFeedback feedback : styleFeedbacks) {
                translator.translateStyleBody(prefLanguage, feedback);
            }
        }
        if (metricsNeeded) {
            for (MetricsFeedback feedback : metricsFeedbacks) {
                translator.translateMetricsBody(prefLanguage, feedback);
            }
        }
//        if (classNeeded) {
//            for (Feedback feedback : classFeedbacks) {
//                translator.translateBody(prefLanguage, feedback);
//            }
//        }
    }


    public static void main(String[] args) {
        long start = System.nanoTime();
        String code = "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "class GrayCode {\n" +
                "    GrayCode() {\n" +
                "    }\n" +
                "\n" +
                "    public static List<String> grayCodeStrings(int n) {\n" +
                "        List<String> list = new ArrayList();\n" +
                "        if (n == 0) {\n" +
                "            list.add(\"\");\n" +
                "            return list;\n" +
                "        } else if (n == 1) {\n" +
                "            list.add(\"0\");\n" +
                "            list.add(\"1\");\n" +
                "            return list;\n" +
                "        } else {\n" +
                "            List<String> prev = grayCodeStrings(n - 1);\n" +
                "            list.addAll(prev);\n" +
                "\n" +
                "            for(int i = prev.size() - 1; i >= 0; --i) {\n" +
                "                String bits = \"abcccc\";\n" +
                "                list.set(i, \"0\" + bits);\n" +
                "                list.add(\"1\" + bits);\n" +
                "            }\n" +
                "\n" +
                "            return list;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    private int anotherMethod(boolean a, boolean b, double c) {\n" +
                "        if (a) {\n" +
                "            if (b) {\n" +
                "                return (int)c;\n" +
                "            }\n" +
                "\n" +
                "            if (c >= 43.0D) {\n" +
                "                return 0;\n" +
                "            }\n" +
                "\n" +
                "            if (a && !b && c < 231.0D && this.doSomething()) {\n" +
                "                return 2147483647;\n" +
                "            }\n" +
                "\n" +
                "            if ((int)c == 5) {\n" +
                "                return (int)(4.0D * c);\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        return 0;\n" +
                "    }\n" +
                "\n" +
                "    private boolean doSomething() {\n" +
                "        return false;\n" +
                "    }\n" +
                "}";

        QfMainSettings qfMainSettings = new QfMainSettings();
        qfMainSettings.setSyntaxLevel(CheckLevel.ADVANCED.name());
        qfMainSettings.setSemanticNeeded("false");
        qfMainSettings.setStyleNeeded("false");
        qfMainSettings.setMetricsNeeded("true");
        qfMainSettings.setPreferredLanguage("en");


        MainSettings mainSettingsConfiguratorConf = new MainSettings(qfMainSettings);

        QfSemanticSettings qfSemanticSettings = new QfSemanticSettings();
//        qfSemanticSettings.setFilePath("src/main/resources/exam-results/src");
//        qfSemanticSettings.setMethodName("grayCodeStrings");
//        qfSemanticSettings.setRecursionAllowed("true");
//        qfSemanticSettings.setWhileLoop("-1");
//        qfSemanticSettings.setForLoop("2");
//        qfSemanticSettings.setForEachLoop("-1");
//        qfSemanticSettings.setIfElseStmt("0");
//        qfSemanticSettings.setDoWhileLoop("-1");
//        qfSemanticSettings.setReturnType("int");

//        SemanticSettingReader semanticSettingReader = SemanticSettingReader.createSemanticConfigurator(qfSemanticSettings);


        QfStyleSettings qfStyleSettings = new QfStyleSettings();
        qfStyleSettings.setNamesLevel("ADV");
        qfStyleSettings.setComplexityLevel("ADV");
        qfStyleSettings.setBasisLevel("ADVANCED");
        qfStyleSettings.setClassLength("10");
        qfStyleSettings.setMethodLength("10");


        StyleChecker styleChecker = StyleChecker.builder().qfStyleSettings(qfStyleSettings).build();

        SemanticChecker semanticChecker = SemanticChecker.builder().qfSemanticSettings(qfSemanticSettings).build();

        QfMetricsSettings qfMetricsSettings = new QfMetricsSettings();
        qfMetricsSettings.setAmc("0.5", "1.0");
        qfMetricsSettings.setAmcNoMax("false");
        qfMetricsSettings.setCa("0.5", "1.0");
        qfMetricsSettings.setCaNoMax("false");
        qfMetricsSettings.setCam("0.5", "1.0");
        qfMetricsSettings.setCamNoMax("false");
        qfMetricsSettings.setCam("0.5", "1.0");
        qfMetricsSettings.setCamNoMax("false");
        qfMetricsSettings.setCbm("0.5", "1.0");
        qfMetricsSettings.setCbmNoMax("false");
        qfMetricsSettings.setCbo("0.5", "1.0");
        qfMetricsSettings.setCboNoMax("false");
        qfMetricsSettings.setCc("0.5", "3.0");
        qfMetricsSettings.setCcNoMax("false");
        qfMetricsSettings.setCe("0.5", "1.0");
        qfMetricsSettings.setCeNoMax("false");
        qfMetricsSettings.setDam("0.5", "1.0");
        qfMetricsSettings.setDamNoMax("false");
        qfMetricsSettings.setDit("0.5", "1.0");
        qfMetricsSettings.setDitNoMax("false");
        qfMetricsSettings.setIc("0.5", "1.0");
        qfMetricsSettings.setIcNoMax("false");
        qfMetricsSettings.setLcom("0.5", "1.0");
        qfMetricsSettings.setLcomNoMax("false");
        qfMetricsSettings.setLcom3("0.5", "1.0");
        qfMetricsSettings.setLcom3NoMax("false");
        qfMetricsSettings.setLoc("15.0", "60.0");
        qfMetricsSettings.setLocNoMax("false");
        qfMetricsSettings.setMoa("0.5", "1.0");
        qfMetricsSettings.setMoaNoMax("false");
        qfMetricsSettings.setMfa("0.5", "1.0");
        qfMetricsSettings.setMfaNoMax("false");
        qfMetricsSettings.setNoc("0.0", "5.0");
        qfMetricsSettings.setNocNoMax("false");
        qfMetricsSettings.setNpm("0.5", "1.0");
        qfMetricsSettings.setNpmNoMax("false");
        qfMetricsSettings.setRfc("0.5", "1.0");
        qfMetricsSettings.setRfcNoMax("false");
        qfMetricsSettings.setWmc("0.5", "1.0");
        qfMetricsSettings.setWmcNoMax("false");
        qfMetricsSettings.setAmcSuggestionMax("WHAT ARE YOU DOING?!?!");
        qfMetricsSettings.includeOnlyPublicClasses("false");

        MetricsChecker metricsChecker = MetricsChecker.builder().qfMetricsSettings(qfMetricsSettings).build();


        //SyntaxErrorAnalyser syntaxErrorAnalyser = SyntaxErrorAnalyser.builder().targetProject("src/main/resources/testProject").build();
        SyntaxErrorAnalyser syntaxErrorAnalyser = SyntaxErrorAnalyser.builder().stringAnswer(code).build();


        MassExecutor massE = new MassExecutor(styleChecker, semanticChecker, syntaxErrorAnalyser, metricsChecker, null, null, mainSettingsConfiguratorConf);

        massE.execute();

        //todo false Alarm: Here was Semicolon expected!

        for (SyntaxFeedback syntaxFeedback : massE.getSyntaxFeedbacks()) {
            System.out.println(syntaxFeedback);
        }

        for (Feedback s : massE.semanticFeedbacks) {
            System.out.println(s.getBody());
        }

//        for (Feedback s : massE.classFeedbacks) {
//            System.out.println(s.getBody());
//            System.out.println("-----------------------------------------------------------------");
//        }

        /*
        for Style Errors
         */

        List<StyleFeedback> feedbacks = massE.styleFeedbacks;

        for (StyleFeedback f : feedbacks) {
            System.out.println(f.getDesc());
            System.out.println(f.getContent());
            System.out.println(f.getLine());
            System.out.println(f.getExample());
            System.out.println("-----------------------------------------------------------------");
        }

        List<MetricsFeedback> metricsFeedbacks = massE.metricsFeedbacks;
        for (MetricsFeedback df : metricsFeedbacks) {
            System.out.println(df);
            System.out.println("---------------------");
        }

        /*
        for Syntax Errors
         */
        List<SyntaxFeedback> arrayList = massE.syntaxFeedbacks;
        for (SyntaxFeedback s : arrayList) {
            System.out.println(s.getBody());
            System.out.println(s.getBody());
            System.out.println(s.getSolutionExample());
            System.out.println("--------0T0----------");
        }
        long end = System.nanoTime() - start;
        System.out.println("Feedback generated in: " + end * Math.pow(10.0, -9.0) + " sec");
    }
}
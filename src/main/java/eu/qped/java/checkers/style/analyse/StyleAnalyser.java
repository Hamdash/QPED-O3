package eu.qped.java.checkers.style.analyse;

import eu.qped.framework.CheckLevel;
import eu.qped.java.checkers.mass.QfStyleSettings;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.checkers.style.analyse.pmd.MassSupportedEditablePmdRules;
import eu.qped.java.checkers.style.analyse.pmd.PmdConfigException;
import eu.qped.java.checkers.style.analyse.pmd.PmdRulesSets;
import eu.qped.java.checkers.style.analyse.pmd.XmlFileManager;
import eu.qped.java.checkers.style.analyse.reportModel.StyleAnalysisReport;
import eu.qped.java.checkers.style.analyse.reportModel.StyleAnalysisReportParser;
import eu.qped.java.checkers.style.config.StyleSettings;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.checkers.syntax.SyntaxSetting;
import eu.qped.java.utils.SupportedLanguages;
import lombok.*;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import org.apache.logging.log4j.LogManager;


/**
 * Checker class to check the code on Style violations
 * The style checker uses PMD-Core to do so
 *
 * @author Basel Alaktaa
 * @version 1.4
 * @since 08.5.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StyleAnalyser {

    private String targetPath;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private StyleSettings styleSettings;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private XmlFileManager xmlFileManager;


    public StyleAnalysisReport analyse() {
        xmlFileManager = new XmlFileManager();
        addNameRules(styleSettings.getNamesLevel());
        addComplexityRules(styleSettings.getComplexityLevel());
        addBasicRules(styleSettings.getBasisLevel());
        applySettingsOnMainRuleset();
        executePMD();
        return buildStyleReport();
    }


    private StyleAnalysisReport buildStyleReport() {
        StyleAnalysisReportParser mapper = StyleAnalysisReportParser.builder().build();
        return mapper.parse();
    }

    /**
     * to add rules from a specific Rule set to the main Ruleset according to a level
     *
     * @param level the level to determine the concrete Ruleset that we need
     */
    private void addNameRules(final CheckLevel level) {
        if (level.equals(CheckLevel.INTERMEDIATE)) {
            xmlFileManager.addToMainRuleset(PmdRulesSets.ADVANCED_NAMING_RULES_SET);
        } else if (level.equals(CheckLevel.ADVANCED)) {
            xmlFileManager.addToMainRuleset(PmdRulesSets.PROFESSIONAL_NAMING_RULES_SET);
        } else {
            xmlFileManager.addToMainRuleset(PmdRulesSets.BEGINNER_NAMING_RULES_SET);
        }
    }

    private void addComplexityRules(final CheckLevel level) {
        if (level.equals(CheckLevel.INTERMEDIATE)) {
            xmlFileManager.addToMainRuleset(PmdRulesSets.ADVANCED_COMPLEXITY_RULES_SET);
        } else if (level.equals(CheckLevel.ADVANCED)) {
            xmlFileManager.addToMainRuleset(PmdRulesSets.PROFESSIONAL_COMPLEXITY_RULES_SET);
        } else {
            xmlFileManager.addToMainRuleset(PmdRulesSets.BEGINNER_COMPLEXITY_RULES_SET);
        }
    }

    private void addBasicRules(final CheckLevel level) {
        if (level.equals(CheckLevel.INTERMEDIATE)) {
            xmlFileManager.addToMainRuleset(PmdRulesSets.ADVANCED_BASIC_RULES_SET);
        } else if (level.equals(CheckLevel.ADVANCED)) {
            xmlFileManager.addToMainRuleset(PmdRulesSets.PROFESSIONAL_BASIC_RULES_SET);
        } else {
            xmlFileManager.addToMainRuleset(PmdRulesSets.BEGINNER_BASIC_RULES_SET);
        }
    }

    private void applySettingsOnMainRuleset() {
        try {
            if (styleSettings.getMaxClassLength() != -1) {
                xmlFileManager.editProperty(
                        MassSupportedEditablePmdRules.EXCESSIVE_CLASS_LENGTH, String.valueOf(styleSettings.getMaxClassLength()
                        ), "minimum");
            }
            if (styleSettings.getMaxMethodLength() != -1) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.EXCESSIVE_METHOD_LENGTH, String.valueOf(styleSettings.getMaxMethodLength()), "minimum");
            }
            if (styleSettings.getMaxFieldsCount() != -1) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.TOO_MANY_FIELDS, String.valueOf(styleSettings.getMaxFieldsCount()), "maxfields");
            }
            if (styleSettings.getMaxCycloComplexity() != -1) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.CYCLOMATIC_COMPLEXITY, String.valueOf(styleSettings.getMaxCycloComplexity()), "methodReportLevel");
            }
            if (!styleSettings.getVarNamesRegEx().equals("undefined") && !styleSettings.getVarNamesRegEx().equals("-1")) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.LOCAL_VARIABLE_NAMING_CONVENTIONS, String.valueOf(styleSettings.getVarNamesRegEx()), "localVarPattern");
            }
            if (!styleSettings.getMethodParameterNamesRegEx().equals("undefined")) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.METHOD_PARAMETER_NAMING_CONVENTIONS, String.valueOf(styleSettings.getMethodParameterNamesRegEx()), "methodParameterPattern");
            }
            if (!styleSettings.getMethodParameterNamesRegEx().equals("undefined")) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.METHOD_PARAMETER_NAMING_CONVENTIONS, String.valueOf(styleSettings.getMethodParameterNamesRegEx()), "finalMethodParameterPattern");
            }
            if (!styleSettings.getMethodNamesRegEx().equals("undefined") && !styleSettings.getMethodNamesRegEx().equals("-1")) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.METHOD_NAMING_CONVENTIONS, String.valueOf(styleSettings.getMethodNamesRegEx()), "methodPattern");
            }
            if (!styleSettings.getClassNameRegEx().equals("undefined") && !styleSettings.getClassNameRegEx().equals("-1")) {
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.CLASS_NAMING_CONVENTIONS, String.valueOf(styleSettings.getClassNameRegEx()), "classPattern");
                xmlFileManager.editProperty(MassSupportedEditablePmdRules.CLASS_NAMING_CONVENTIONS, String.valueOf(styleSettings.getClassNameRegEx()), "abstractClassPattern");
            }
        } catch (PmdConfigException e) {
            LogManager.getLogger(getClass()).throwing(e);
        }
    }


    private void executePMD() {
        final PMDConfiguration configuration = new PMDConfiguration();
        configuration.setInputPaths(targetPath);
        configuration.setRuleSets(xmlFileManager.getFilename());
        configuration.setReportFormat("json");
        configuration.setReportFile("src/main/java/eu/qped/java/checkers/style/resources/report.json");
        PMD.runPmd(configuration);
    }

    public static void main(String[] args) {

        String codeToCompile = "double krt(double A, double k, double d){\n" +
                "    int K,L;\n" +
                "    return  A;\n" +
                "}\n" +
                "\n" +
                "double krtH(double a, double k, double d, double x_n){\n" +
                "    return a;\n" +
                "}";

        SyntaxChecker syntaxChecker = SyntaxChecker.builder().build();
        syntaxChecker.setStringAnswer(codeToCompile);
        var setting = SyntaxSetting.builder().checkLevel(CheckLevel.BEGINNER).language("en").build();
        syntaxChecker.setSyntaxSetting(setting);
        syntaxChecker.check();



        var qfStyleSetting = QfStyleSettings
                .builder()
                .basisLevel("BEGINNER")
                .complexityLevel("BEGINNER")
                .namesLevel("BEGINNER")
                .classLength("-1")
                .methodLength("-1")
                .cyclomaticComplexity("-1")
                .fieldsCount("-1")
                .variableNamePattern("[a-z][a-zA-Z0-9]*")
                .methodNamePattern("[a-z][a-zA-Z0-9]*")
                .classNamePattern("[A-Z][a-zA-Z0-9_]*")
                .methodParameterNamePattern("[A-Z][a-zA-Z0-9_]*")
                .language(SupportedLanguages.GERMAN)
                .build();
        var styleChecker = StyleChecker.builder()
                .qfStyleSettings(qfStyleSetting)
                .build();
        styleChecker.setTargetPath(syntaxChecker.getAnalyseReport().getPath());
        styleChecker.check().forEach(System.out::println);

    }
}

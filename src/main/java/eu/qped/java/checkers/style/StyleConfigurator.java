package eu.qped.java.checkers.style;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;

import eu.qped.framework.CheckLevel;
import eu.qped.java.checkers.mass.QFStyleSettings;

import java.util.List;

/**
 * Style checker configs manager.
 * The class manage all the settings for the style checker and create a Setting Object to control the checker.
 *
 * @author Basel Alaktaa
 * @version 1.1
 * @since 01.06.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StyleConfigurator {


    private static final CheckLevel DEFAULT_CHECK_LEVEL = CheckLevel.BEGINNER;
    private int maxClassLength;
    private int maxMethodLength;
    private int maxFieldsCount;
    private int maxCycloComplexity;

    private String varNamesRegEx;
    private String methodNamesRegEx;
    private String classNameRegEx;


    private CheckLevel mainLevel;

    private CheckLevel namesLevel;
    private CheckLevel complexityLevel;
    private CheckLevel basisLevel;


    private QFStyleSettings qfStyleSettings;


    private StyleConfigurator(QFStyleSettings qfStyleSettings) {
        this.qfStyleSettings = qfStyleSettings;
        this.readSettings();
    }

    public static StyleConfigurator createStyleConfigurator(QFStyleSettings qfStyleSettings) {
        return new StyleConfigurator(qfStyleSettings);
    }

    public static StyleConfigurator createDefaultStyleConfigurator() {
        return new StyleConfigurator(null);
    }


    protected void readSettings() {

        try {
            maxClassLength = qfStyleSettings.getClassLength() != null ? Integer.parseInt(qfStyleSettings.getClassLength()) : -1;
            maxMethodLength = qfStyleSettings.getMethodLength() != null ? Integer.parseInt(qfStyleSettings.getMethodLength()) : -1;
            maxFieldsCount = qfStyleSettings.getFieldsCount() != null ? Integer.parseInt(qfStyleSettings.getFieldsCount()) : -1;
            maxCycloComplexity = qfStyleSettings.getCycloComplexity() != null ? Integer.parseInt(qfStyleSettings.getCycloComplexity()) : -1;
            varNamesRegEx = qfStyleSettings.getVarName() != null ? qfStyleSettings.getVarName() : "undefined";
            methodNamesRegEx = qfStyleSettings.getMethodName() != null ? qfStyleSettings.getMethodName() : "undefined";
            classNameRegEx = qfStyleSettings.getClassName() != null ? qfStyleSettings.getClassName() : "undefined";
            mainLevel = isCheckLevel(qfStyleSettings.getMainLevel()) ? (CheckLevel.valueOf(qfStyleSettings.getMainLevel())) : CheckLevel.BEGINNER;
            namesLevel = isCheckLevel(qfStyleSettings.getNamesLevel()) ? (CheckLevel.valueOf(qfStyleSettings.getNamesLevel())) : CheckLevel.BEGINNER;
            complexityLevel = isCheckLevel(qfStyleSettings.getCompLevel()) ? (CheckLevel.valueOf(qfStyleSettings.getCompLevel())) : CheckLevel.BEGINNER;
            basisLevel = isCheckLevel(qfStyleSettings.getBasisLevel()) ? (CheckLevel.valueOf(qfStyleSettings.getBasisLevel())) : CheckLevel.BEGINNER;
        } catch (IllegalArgumentException | NullPointerException e) {
            LogManager.getLogger(getClass()).warn(e.getMessage());
        }
    }

    public boolean isCheckLevel(String checkLevel) {
        try {
            List<CheckLevel> checkLevels = List.of(CheckLevel.BEGINNER, CheckLevel.INTERMEDIATE, CheckLevel.ADVANCED);
            return checkLevels.contains(CheckLevel.valueOf(checkLevel));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}


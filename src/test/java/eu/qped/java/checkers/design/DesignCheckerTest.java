package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.configuration.DesignSettingsReader;
import eu.qped.java.checkers.design.data.DesignCheckReport;
import eu.qped.java.checkers.design.utils.TestUtility;
import eu.qped.java.checkers.mass.QFDesignSettings;
import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link DesignChecker}
 *
 * @author Jannik Seus
 */
class DesignCheckerTest {

    private DesignChecker designCheckerEmpty;
    private DesignChecker designCheckerFilled;
    private DesignChecker designCheckerNoArgs;

    private final Field[] fields = DesignChecker.class.getDeclaredFields();;


    @BeforeEach
    void setUp() {
        TestUtility.generateTestClass();

        designCheckerEmpty = DesignChecker.builder().build();
        designCheckerFilled = DesignChecker.builder()
                .designFeedbacks(mock(List.class))
                .qfDesignSettings(mock(QFDesignSettings.class))
                .build();
        designCheckerNoArgs = new DesignChecker();
    }

    @Test
    void testClassFilesPath() throws IllegalAccessException {
        Field classFilesPathField = TestUtility.getFieldByName("CLASSFILES_PATH", fields);
        assert classFilesPathField != null;
        classFilesPathField.setAccessible(true);
        String classFilesPath = (String) classFilesPathField.get(DesignChecker.class);
        classFilesPathField.setAccessible(false);
        assertEquals("src/main/java/eu/qped/java/utils/compiler/compiledFiles", classFilesPath);
    }

    @Test
    void testEmptyDesignChecker() throws IllegalAccessException {
        assertNull(designCheckerEmpty.getDesignFeedbacks());

        Field qfDesignSettingsField = TestUtility.getFieldByName("qfDesignSettings", fields);
        assert qfDesignSettingsField != null;
        qfDesignSettingsField.setAccessible(true);
        assertNull(qfDesignSettingsField.get(designCheckerEmpty));
        qfDesignSettingsField.setAccessible(false);
    }

    @Test
    void testFilledDesignChecker() throws IllegalAccessException {
        assertNotNull(designCheckerFilled.getDesignFeedbacks());

        Field qfDesignSettingsField = TestUtility.getFieldByName("qfDesignSettings", fields);
        assert qfDesignSettingsField != null;
        qfDesignSettingsField.setAccessible(true);
        assertNotNull(qfDesignSettingsField.get(designCheckerFilled));
        qfDesignSettingsField.setAccessible(false);
    }
    @Test
    void testNoArgsDesignChecker() throws IllegalAccessException {
        assertNull(designCheckerNoArgs.getDesignFeedbacks());

        Field qfDesignSettingsField = TestUtility.getFieldByName("qfDesignSettings", fields);
        assert qfDesignSettingsField != null;
        qfDesignSettingsField.setAccessible(true);
        assertNull(qfDesignSettingsField.get(designCheckerNoArgs));
        qfDesignSettingsField.setAccessible(false);
    }


    @Test
    void testCheck() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        DesignChecker designCheckerCustom = DesignChecker.builder().qfDesignSettings(TestUtility.generateSampleQFDesignSettings()).build();

        DesignCheckReport designCheckReport = DesignCheckReport.builder().build();
        DesignSettingsReader designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(TestUtility.generateSampleQFDesignSettings()).build();
        DesignSettings designSettings = designSettingsReader.readDesignSettings();

        List<File> classFiles
                = ExtractJavaFilesFromDirectory.builder().dirPath("src/main/java/eu/qped/java/utils/compiler/compiledFiles").build().filesWithExtension("class");
        String[] pathsToClassFiles = classFiles.stream().map(File::getPath).toArray(String[]::new);

        Method runCkjmExtendedMethod = designCheckerCustom.getClass().getDeclaredMethod("runCkjmExtended", DesignCheckReport.class, String[].class);
        runCkjmExtendedMethod.setAccessible(true);
        runCkjmExtendedMethod.invoke(designCheckerCustom, designCheckReport, pathsToClassFiles);
        runCkjmExtendedMethod.setAccessible(false);
        designCheckReport.setPathsToClassFiles(List.of(pathsToClassFiles));
        List<DesignFeedback> designFeedbacks = DesignFeedback.generateDesignFeedbacks(designCheckReport.getMetricsMap(), designSettings);
        designCheckerCustom.setDesignFeedbacks(designFeedbacks);

        assertEquals(designCheckReport, designCheckerCustom.check());
        assertEquals(designFeedbacks, designCheckerCustom.getDesignFeedbacks());
    }
    @Test
    void testToString() throws IllegalAccessException {
        Field qfDesignSettingsField = TestUtility.getFieldByName("qfDesignSettings", fields);
        assert qfDesignSettingsField != null;
        qfDesignSettingsField.setAccessible(true);

        assertEquals(
                designCheckerFilled.toString(),
                "DesignChecker{" +
                "feedbacks=" + designCheckerFilled.getDesignFeedbacks() +
                ", qfDesignSettings=" + qfDesignSettingsField.get(designCheckerFilled) +
                '}');
        qfDesignSettingsField.setAccessible(false);

    }


}
package eu.qped.java.checkers.design.utils;

import eu.qped.java.checkers.mass.QFDesignSettings;
import eu.qped.java.utils.compiler.Compiler;

import java.lang.reflect.Field;

/**
 * @author Jannik Seus
 */
public class TestUtility {

    public static Field getFieldByName(String name, Field[] fields) {
        for (Field f : fields) {
            if (f.getName().equals(name)) return f;
        }
        return null;
    }

    public static void generateTestClass() {
        Compiler c = Compiler.builder().build();
        String stringAnswer = "    import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "    public class DCTest{\n" +
                "        List<String> xx(){\n" +
                "            List list = new ArrayList();\n" +
                "            list.add(\"8888\");\n" +
                "            return list;\n" +
                "        }\n" +
                "    }";
        c.compileFromString(stringAnswer);
    }

    public static QFDesignSettings generateSampleQFDesignSettings() {
        QFDesignSettings qfDesignSettings = new QFDesignSettings();
        qfDesignSettings.setAmc("0.5", "1.0");
        qfDesignSettings.setCa("0.5", "1.0");
        qfDesignSettings.setCam("0.5", "1.0");
        qfDesignSettings.setCbm("0.5", "1.0");
        qfDesignSettings.setCbo("0.5", "1.0");
        qfDesignSettings.setCc("0.5", "1.0");
        qfDesignSettings.setCe("0.5", "1.0");
        qfDesignSettings.setDam("0.5", "1.0");
        qfDesignSettings.setDit("0.5", "1.0");
        qfDesignSettings.setIc("0.5", "1.0");
        qfDesignSettings.setLcom("0.5", "1.0");
        qfDesignSettings.setLcom3("0.5", "1.0");
        qfDesignSettings.setLoc("0.5", "1.0");
        qfDesignSettings.setMoa("0.5", "1.0");
        qfDesignSettings.setMfa("0.5", "1.0");
        qfDesignSettings.setNoc("0.5", "1.0");
        qfDesignSettings.setNpm("0.5", "1.0");
        qfDesignSettings.setRfc("0.5", "1.0");
        qfDesignSettings.setWmc("0.5", "1.0");
        return qfDesignSettings;
    }
}

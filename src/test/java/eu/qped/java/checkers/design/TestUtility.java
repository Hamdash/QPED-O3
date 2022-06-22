package eu.qped.java.checkers.design;

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
}

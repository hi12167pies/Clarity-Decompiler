package cf.pies.decompiler.utils;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

@SuppressWarnings("all")
public class Reflections {
    /**
     * Sets the field in the object to the value
     */
    public static void setField(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets the field from the object
     */
    @Nonnull
    public static <T> T getField(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
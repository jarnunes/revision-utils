package com.jnunes.revision.reflection;

import com.jnunes.revision.EntityField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static <T> List<Field> getNonJavaLangFields(Class<T> clazz) {
        List<Field> nonJavaLangFields = new ArrayList<>();

        Stream.of(clazz.getDeclaredFields()).filter(it -> !javaLangField(it)).forEach(nonJavaLangFields::add);
        return nonJavaLangFields;
    }

    public static boolean javaLangField(Field field) {
        return javaLangField(field.getType());
    }

    public static boolean nonJavaLangField(EntityField entityField) {
        return !javaLangField(entityField.getType());
    }


    public static boolean javaLangField(Class<?> fieldClazzType) {
        return fieldClazzType.isPrimitive() || fieldClazzType.getName().startsWith("java.lang.");
    }
}

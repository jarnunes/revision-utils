package com.jnunes.revision.reflection;

import com.jnunes.revision.AuditorException;
import com.jnunes.revision.RevisionField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class FieldHandler<T> {
    T entity;

    public FieldHandler(T entity) {
        this.entity = entity;
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> fieldsMap = new HashMap<>();

        Field[] entityFields = entity.getClass().getDeclaredFields();

        try {
            for (Field field : entityFields) {

                field.setAccessible(true);
                fieldsMap.put(field.getName(), field.get(entity));
            }

            return fieldsMap;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new AuditorException("An error was occurred on field get value.", e);
        }
    }

    public <R> void entitySetterValues(Function<RevisionField, R> getFieldValue, List<RevisionField> revisionFields) {
        try {
            for (RevisionField revisionField : revisionFields) {
                Field field = entity.getClass().getField(revisionField.getField());
                field.setAccessible(true);
                field.set(entity, getFieldValue.apply(revisionField));
            }
        } catch (NoSuchFieldException e) {
            throw new AuditorException("Field not found exception.", e);
        } catch (IllegalAccessException e) {
            throw new AuditorException("An error occurred on set field value. ", e);
        }
    }

}

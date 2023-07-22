package com.jnunes.revision;

import com.jnunes.revision.vo.EntityField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FieldHandler<T> {
    T entity;

    protected FieldHandler(T entity) {
        this.entity = entity;
    }

    public static <T> FieldHandler<T> of(T entity) {
        return new FieldHandler<>(entity);
    }

    public List<EntityField> build() {
        List<EntityField> entityFields = new ArrayList<>();

        Stream.of(entity.getClass().getDeclaredFields()).map(this::createEntityField).forEach(entityFields::add);
        return entityFields;
    }

    protected EntityField createEntityField(Field field) {
        try {
            field.setAccessible(true);

            EntityField entityField = new EntityField();
            entityField.setName(field.getName());
            entityField.setValue(field.get(entity));
            entityField.setType(field.getType());
            return entityField;

        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RevisionException("Can not get field [" + field.getName() + "] on class ["
                    + entity.getClass().getCanonicalName() + "]", e);
        }

    }


}

package com.jnunes.revision;

import com.jnunes.revision.vo.EntityField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FieldHandler<T> {
    T entity;

    private FieldHandler(T entity) {
        this.entity = entity;
    }

    public static <T> FieldHandler<T> of(T entity){
        return new FieldHandler<>(entity);
    }

    public List<EntityField<T>> build() {
        List<EntityField<T>> entityFields = new ArrayList<>();

        Stream.of(entity.getClass().getDeclaredFields()).map(this::createEntityField).forEach(entityFields::add);
        return entityFields;
    }

    @SuppressWarnings("unchecked")
    private EntityField<T> createEntityField(Field field) {
        try {
            field.setAccessible(true);

            EntityField<T> entityField = new EntityField<>();
            entityField.setName(field.getName());
            entityField.setValue((T)field.get(entity));
            entityField.setType(field.getType());
            return entityField;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RevisionException("An error was occurred during get value of field '" + field.getName() + "'", e);
        }

    }


}

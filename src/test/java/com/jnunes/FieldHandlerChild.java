package com.jnunes;

import com.jnunes.revision.FieldHandler;
import com.jnunes.revision.vo.EntityField;

import java.lang.reflect.Field;

public class FieldHandlerChild<T> extends FieldHandler<T> {

    protected FieldHandlerChild(T entity) {
        super(entity);
    }

    public static <T> FieldHandlerChild<T> of(T entity) {
        return new FieldHandlerChild<>(entity);
    }

    @Override
    public EntityField createEntityField(Field field) {
        return super.createEntityField(field);
    }
}

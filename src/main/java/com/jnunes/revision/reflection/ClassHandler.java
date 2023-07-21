package com.jnunes.revision.reflection;

import com.jnunes.revision.AuditorException;

public class ClassHandler<T> {

    private final Class<T> clazz;

    public ClassHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ConstructorHandler<T> getConstructor() {
        try {
            return new ConstructorHandler<>(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            throw new AuditorException("Method not found exception.", e);
        }
    }

    public T createInstance() {
        return getConstructor().invoke();
    }

}

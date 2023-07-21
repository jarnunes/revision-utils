package com.jnunes.revision.reflection;

import com.jnunes.revision.AuditorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorHandler<T> {

    private final Constructor<T> constructor;

    public ConstructorHandler(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    public T invoke() {
        try {
            return this.constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            throw new AuditorException("An error was occurred on class " + constructor.getClass().getCanonicalName()
                    + " instantiation.", e);
        } catch (IllegalAccessException e) {
            throw new AuditorException("An error was occurred. Maybe constructor of class "
                    + constructor.getClass().getCanonicalName() + " is private.", e);
        }
    }
}

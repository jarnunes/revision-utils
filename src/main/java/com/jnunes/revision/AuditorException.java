package com.jnunes.revision;

public class AuditorException extends RuntimeException {

    public AuditorException(String message) {
        super(message);
    }

    public AuditorException(IllegalAccessException exception) {
        super(exception);
    }

    public AuditorException(String message, ReflectiveOperationException exception) {
        super(exception);
    }

}

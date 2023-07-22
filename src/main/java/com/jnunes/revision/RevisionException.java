package com.jnunes.revision;

public class RevisionException extends RuntimeException {

    public RevisionException(String message) {
        super(message);
    }

    public RevisionException(IllegalAccessException exception) {
        super(exception);
    }

    public RevisionException(String message, ReflectiveOperationException exception) {
        super(exception);
    }

}

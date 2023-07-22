package com.jnunes.revision;

public class RevisionException extends RuntimeException {

    public RevisionException(String message) {
        super(message);
    }

    public RevisionException(String message, Throwable exception) {
        super(message,exception);
    }

}

package com.jnunes.revision.vo;

public abstract class VOBase {

    protected String append(String field, Object value) {
        return field + "=" + value + "; ";
    }
}

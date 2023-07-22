package com.jnunes.revision.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RevisionField<T> extends VOBase {
    private String name;
    private RevisionValue<T> revisionValue;

    public String toString() {
        return "RevisionField [ " +
                append("name", name) +
                revisionValue.toString() + "]";
    }
}

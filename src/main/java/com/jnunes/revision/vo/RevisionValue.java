package com.jnunes.revision.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class RevisionValue<T> extends VOBase {

    private T current;
    private T updated;

    private boolean changed;

    public void checkIfChanged() {
        this.changed = !Objects.equals(current, updated);
    }

    public String toString() {
        return "RevisionValue [ " +
                append("current", current) +
                append("updated", updated) +
                append("changed", changed) + " ]";
    }
}

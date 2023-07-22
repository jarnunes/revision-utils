package com.jnunes.revision;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class RevisionField {
    private String fieldName;
    private Object currentValue;
    private Object newValue;
    private RevisionField currentCustomFieldValue;
    private RevisionField newCustomFieldValue;

    private boolean changed;

    public void checkIfChanged() {
        this.changed = !Objects.equals(currentValue, newValue);
    }

    public String toString() {
        return "FieldRevision [" + append("fieldName", fieldName) +
                append("currentValue", currentValue) +
                append("newValue", newValue) +
                append("changed", changed) + "]";
    }

    private String append(String field, Object value) {
        return field + "=" + value + "; ";
    }
}

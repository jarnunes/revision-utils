package com.jnunes.revision.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RevisionValue {

    private Object current;
    private Object updated;

    private boolean changed;

    public void checkIfChanged() {
        this.changed = !Objects.equals(current, updated);
    }

}

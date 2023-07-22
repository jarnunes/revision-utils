package com.jnunes.revision.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RevisionField<T> {
    private String name;
    private RevisionValue<T> revisionValue;

}

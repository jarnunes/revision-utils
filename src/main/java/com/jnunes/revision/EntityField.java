package com.jnunes.revision;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntityField {
    private String name;
    private Object value;
    private Class<?> type;
}

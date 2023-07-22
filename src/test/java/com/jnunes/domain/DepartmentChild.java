package com.jnunes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentChild extends Department {
    private Integer members;

}

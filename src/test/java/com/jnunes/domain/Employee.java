package com.jnunes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "department")
@ToString
public class Employee {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Double salary;
    private PositionType position;
    private Department department;
}

package com.jnunes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "department")
public class Employee {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Double salary;
    private PositionType position;
    private Department department;

    public String toString() {
        return this.getClass().getSimpleName() + "[ " +
                append("id", id) +
                append("name", name) +
                append("email", email) +
                append("age", age) +
                append("salary", salary) +
                append("position", position) +
                append("department", department) + " ]";
    }

    private String append(String field, Object value) {
        return field + "=" + value + "; ";
    }
}

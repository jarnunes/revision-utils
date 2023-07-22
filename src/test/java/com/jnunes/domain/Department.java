package com.jnunes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"manager", "employees"})
@ToString(exclude = "employees")
public class Department {
    private Long id;
    private String name;
    private Employee manager;
    private String description;
    private List<Employee> employees = new ArrayList<>();
    private LocalDate creationDate;
}

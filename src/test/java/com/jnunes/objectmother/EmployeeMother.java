package com.jnunes.objectmother;

import com.jnunes.domain.Employee;
import com.jnunes.domain.PositionType;

public class EmployeeMother {

    public static Employee buildEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmail("employee.1.@email.com");
        employee.setName("Employee 1");
        employee.setAge(25);
        employee.setPosition(PositionType.CEO);
        employee.setSalary(35000d);
        employee.setDepartment(DepartmentMother.buildDepartment());
        return employee;
    }
}

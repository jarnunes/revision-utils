package com.jnunes;

import com.jnunes.domain.Department;
import com.jnunes.revision.RevisionProcessor;
import com.jnunes.domain.Employee;
import com.jnunes.domain.PositionType;

public class App {
    public static void main(String[] args) {
        Department department1 = new Department();
        department1.setId(2L);
        department1.setName("Estoque");
        department1.setDescription("Description 1");

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setEmail("christian_matty@employee.com");
        employee1.setName("Christian Matte");
        employee1.setAge(25);
        employee1.setPosition(PositionType.CEO);
        employee1.setSalary(35000d);
        employee1.setDepartment(department1);

        Department department2 = new Department();
        department2.setId(1L);
        department2.setName("Financeiro");
        department2.setDescription("Description 2");

        Employee employee2 = new Employee();
        employee2.setId(0L);
        employee2.setName("Anthony Chris Dmark");
        employee2.setEmail("marcos@employee.com");
        employee2.setAge(25);
        employee2.setPosition(PositionType.CEO);
        employee2.setSalary(35000d);
        employee2.setDepartment(department2);


        RevisionProcessor<Employee> employeeAuditorCheck = new RevisionProcessor<>(employee2, employee1);
        employeeAuditorCheck.build().forEach(fieldRevision ->
                System.out.println(fieldRevision.toString()));

    }
}

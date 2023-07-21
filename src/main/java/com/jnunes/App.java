package com.jnunes;

import com.jnunes.revision.RevisionProcessor;
import com.jnunes.domain.Employee;
import com.jnunes.domain.PositionType;

public class App {
    public static void main(String[] args) {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setEmail("christian_matty@employee.com");
        employee1.setName("Christian Matte");
        employee1.setAge(25);
        employee1.setPosition(PositionType.CEO);
        employee1.setSalary(35000d);

        Employee employee2 = new Employee();
        employee2.setId(0L);
        employee2.setName("Anthony Chris Dmark");
        employee2.setEmail("marcos@employee.com");
        employee2.setAge(25);
        employee2.setPosition(PositionType.CEO);
        employee2.setSalary(35000d);


        RevisionProcessor<Employee> employeeAuditorCheck = new RevisionProcessor<>(employee2, employee1);
        employeeAuditorCheck.getFieldRevisions().forEach(fieldRevision -> {
            System.out.println(fieldRevision.toString());
            System.out.println();
        });

        // possivelmente tem que configurar o java para compilar o nome dos atributos. Deve que que esta compilando com nome random.
//        employeeAuditorCheck.createInstanceWithRevisedFields().forEach((key, value) -> {
//            System.out.println("key = " + key);
//            System.out.println("value = " + value.toString());
//        });
    }
}

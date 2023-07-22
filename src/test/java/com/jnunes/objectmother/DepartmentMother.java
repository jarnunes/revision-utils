package com.jnunes.objectmother;

import com.jnunes.domain.Department;
import com.jnunes.domain.DepartmentChild;

import java.time.LocalDate;

public class DepartmentMother {
    public static Department buildDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");
        department.setDescription("Description 1");
        return department;
    }

    public static DepartmentChild buildDepartmentChild() {
        DepartmentChild departmentChild = new DepartmentChild();
        departmentChild.setId(1L);
        departmentChild.setName("Department Child 1");
        departmentChild.setDescription("Description 1");
        departmentChild.setCreationDate(LocalDate.now().minusDays(300));
        departmentChild.setMembers(200);
        return departmentChild;
    }


}

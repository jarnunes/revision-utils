package com.jnunes;

import com.jnunes.domain.Department;
import com.jnunes.domain.DepartmentChild;
import com.jnunes.domain.Employee;
import com.jnunes.domain.PositionType;
import com.jnunes.objectmother.DepartmentMother;
import com.jnunes.objectmother.EmployeeMother;
import com.jnunes.revision.FieldHandler;
import com.jnunes.revision.RevisionException;
import com.jnunes.revision.RevisionProcessor;
import com.jnunes.revision.vo.EntityField;
import com.jnunes.revision.vo.RevisionField;
import com.jnunes.revision.vo.RevisionValue;
import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }


    public void testFieldHandler() {
        FieldHandler<Employee> fieldHandler = FieldHandler.of(EmployeeMother.buildEmployee());
        List<EntityField> entityFields = fieldHandler.build();

        assertFalse(entityFields.isEmpty());
        assertEquals(entityFields.size(), 7);
    }

    public void testRevisionProcessorChangedFieldId() {
        Employee employee = EmployeeMother.buildEmployee();
        Employee employee1 = EmployeeMother.buildEmployee();
        employee1.setId(2L);

        List<RevisionField> revisionProcessor = RevisionProcessor.of(employee, employee1).build();

        Predicate<RevisionField> equalsName = revisionField -> Objects.equals(revisionField.getName(), "id");

        RevisionValue revisionValue = revisionProcessor.stream().filter(equalsName).map(RevisionField::getRevisionValue)
                .findFirst().orElse(new RevisionValue());

        assertNotNull(revisionValue.getCurrent());
        assertNotNull(revisionValue.getUpdated());
        assertTrue(revisionValue.isChanged());
    }

    public void testRevisionProcessorChangedFields() {
        Employee employee = EmployeeMother.buildEmployee();
        Employee employee1 = EmployeeMother.buildEmployee();
        employee1.setId(2L);
        employee1.setPosition(PositionType.ADMIN_ASSISTANT);

        List<RevisionField> revisionProcessor = RevisionProcessor.of(employee, employee1).onlyUpdated().build();

        assertEquals(revisionProcessor.size(), 2);
        assertTrue(revisionProcessor.get(0).getRevisionValue().isChanged());
        assertTrue(revisionProcessor.get(1).getRevisionValue().isChanged());
    }

    public void testRevisionProcessorChangedFieldNotFoundException() {
        Department department = DepartmentMother.buildDepartment();
        DepartmentChild departmentChild = DepartmentMother.buildDepartmentChild();

        try {
            RevisionProcessor.of(departmentChild, department).onlyUpdated().build();
            fail("Expected RevisionException was not thrown.");
        } catch (RevisionException e) {
            assertTrue(e.getMessage().contains("Field not found exception."));
        }
    }

    public void testFieldHandlerCreateEntityField() {
        Department department = DepartmentMother.buildDepartment();
        FieldHandlerChild<Department> fieldHandler = FieldHandlerChild.of(department);

        Field field = Stream.of(department.getClass().getDeclaredFields())
            .filter(it -> it.getName().equals("id")).findFirst().orElse(null);
        assertNotNull(fieldHandler.createEntityField(field));

    }

    public void testFieldHandlerCreateEntityFieldException() {
        Department department = DepartmentMother.buildDepartment();
        FieldHandlerChild<Department> fieldHandler = FieldHandlerChild.of(department);

        DepartmentChild departmentChild = DepartmentMother.buildDepartmentChild();
        Field field = Stream.of(departmentChild.getClass().getDeclaredFields())
                .filter(it -> it.getName().equals("members")).findFirst().orElse(null);
        assertNotNull(field);

        try {

            fieldHandler.createEntityField(field);

            fail("Expected RevisionException was not thrown.");
        } catch (RevisionException e) {
            String msg = "Can not get field [" + field.getName() + "] on class [" + department.getClass().getCanonicalName() + "]";
            assertTrue(e.getMessage().contains(msg));
        }
    }

}

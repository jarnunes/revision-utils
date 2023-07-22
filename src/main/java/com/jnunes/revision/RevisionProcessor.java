package com.jnunes.revision;

import com.jnunes.revision.reflection.FieldHandler;
import com.jnunes.revision.reflection.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RevisionProcessor<T> {
    private final T currentEntity;
    private final T newEntity;
    private boolean processAttributesWithCustomType;


    public RevisionProcessor(T currentEntity, T newEntity) {
        this.currentEntity = currentEntity;
        this.newEntity = newEntity;
    }

    public RevisionProcessor<T> processAttributesWithCustomType() {
        processAttributesWithCustomType = true;
        return this;
    }

    public List<RevisionField> build() {
        return processAttributesWithCustomType ? getFieldsRevisionProcessCustomFields() : getFieldRevisions();
    }

    private List<RevisionField> getFieldsRevisionProcessCustomFields() {
        List<RevisionField> revisionFields = new ArrayList<>();

        List<EntityField> currentEntityFields = new FieldHandler<>(currentEntity).build();
        List<EntityField> newEntityFields = new FieldHandler<>(newEntity).build();

        currentEntityFields.forEach(entityField -> {
            Object newFieldValue = getNewValue(entityField.getName(), newEntityFields);
            if (ReflectionUtils.nonJavaLangField(entityField)) {
                revisionFields.addAll(processCustomFields(entityField.getValue(), newFieldValue));
            } else {

                RevisionField revisionField = new RevisionField();
                revisionField.setFieldName(entityField.getName());
                revisionField.setCurrentValue(entityField.getValue());
                revisionField.setNewValue(getNewValue(entityField.getName(), newEntityFields));
                revisionField.checkIfChanged();
                revisionFields.add(revisionField);
            }
        });

        return revisionFields;


    }

    private List<RevisionField> getFieldRevisions() {
        List<EntityField> currentEntityFields = new FieldHandler<>(currentEntity).build();
        List<EntityField> newEntityFields = new FieldHandler<>(newEntity).build();
        return processFields(currentEntityFields, newEntityFields);
    }

    private List<RevisionField> processCustomFields(Object current, Object newFields) {
        List<EntityField> currentCustomFieldTypeEntity = new FieldHandler<>(current).build();
        List<EntityField> newCustomFieldTypeEntity = new FieldHandler<>(newFields).build();

        return processFields(currentCustomFieldTypeEntity, newCustomFieldTypeEntity);
    }


    private Object getNewValue(String name, List<EntityField> newEntityFields) {
        Predicate<EntityField> equalsName = entityField -> Objects.equals(name, entityField.getName());

        return newEntityFields.stream().filter(equalsName).findFirst()
                .orElseThrow(() -> new RevisionException("Field not found exception."));
    }

    private List<RevisionField> processFields(List<EntityField> current, List<EntityField> newFields) {
        return RevisionFieldProcessor.of(current, newFields).build();
    }


}

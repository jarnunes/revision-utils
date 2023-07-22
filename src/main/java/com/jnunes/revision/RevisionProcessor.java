package com.jnunes.revision;

import com.jnunes.revision.reflection.FieldHandler;
import com.jnunes.revision.vo.EntityField;
import com.jnunes.revision.vo.RevisionField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<RevisionField<T>> build() {
        return processAttributesWithCustomType ? new ArrayList<>() : getFieldRevisions();
    }

//    private List<RevisionField> getFieldsRevisionProcessCustomFields() {
//        List<RevisionField> revisionFields = new ArrayList<>();
//
//        List<EntityField> currentEntityFields = new FieldHandler<>(currentEntity).build();
//        List<EntityField> newEntityFields = new FieldHandler<>(newEntity).build();
//
//        currentEntityFields.forEach(entityField -> {
//            Object newFieldValue = getNewValue(entityField.getName(), newEntityFields);
//            if (ReflectionUtils.nonJavaLangField(entityField)) {
//                revisionFields.addAll(processCustomFields(entityField.getValue(), newFieldValue));
//            } else {
//
//                RevisionField revisionField = new RevisionField();
//                revisionField.setFieldName(entityField.getName());
//                revisionField.setCurrentValue(entityField.getValue());
//                revisionField.setNewValue(getNewValue(entityField.getName(), newEntityFields));
//                revisionField.checkIfChanged();
//                revisionFields.add(revisionField);
//            }
//        });
//
//        return revisionFields;
//
//
//    }

    private List<RevisionField<T>> getFieldRevisions() {
        List<EntityField<T>> currentEntityFields = new FieldHandler<>(currentEntity).build();
        List<EntityField<T>> newEntityFields = new FieldHandler<>(newEntity).build();
        return processFields(currentEntityFields, newEntityFields);
    }

//    private List<RevisionField<T>> processCustomFields(Object current, Object newFields) {
//        List<EntityField<T>> currentCustomFieldTypeEntity = new FieldHandler<>(current).build();
//        List<EntityField<T>> newCustomFieldTypeEntity = new FieldHandler<>(newFields).build();
//
//        return processFields(currentCustomFieldTypeEntity, newCustomFieldTypeEntity);
//    }


//    private Object getNewValue(String name, List<EntityField<T>> newEntityFields) {
//        return newEntityFields.stream().filter(entityField -> Objects.equals(entityField.getName(), name)).findFirst()
//                .orElseThrow(() -> new RevisionException("Field not found exception."));
//    }

    private List<RevisionField<T>> processFields(List<EntityField<T>> current, List<EntityField<T>> newFields) {
        return FieldProcessor.of(current, newFields).build();
    }


}

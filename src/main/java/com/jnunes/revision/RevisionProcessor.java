package com.jnunes.revision;

import com.jnunes.revision.reflection.ClassHandler;
import com.jnunes.revision.reflection.FieldHandler;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public class RevisionProcessor<T> {
    private final T currentEntity;
    private final T newEntity;


    private final Class<T> entityClass ;

    //= (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @SuppressWarnings("unchecked")
    public RevisionProcessor(T currentEntity, T newEntity) {
        this.currentEntity = currentEntity;
        this.newEntity = newEntity;
        this.entityClass = (Class<T>) currentEntity.getClass();
    }

    public Map<String, T> createInstanceWithRevisedFields() {
        Map<String, T> revisedEntity = new HashMap<>();

        T currentEntityInstance = new ClassHandler<>(entityClass).getConstructor().invoke();
        FieldHandler<T> currentInstanceFieldHandler = new FieldHandler<>(currentEntityInstance);
        currentInstanceFieldHandler.entitySetterValues(RevisionField::getCurrentValue, getRevisedFields());

        T newEntityInstance = new ClassHandler<>(entityClass).getConstructor().invoke();
        FieldHandler<T> newInstanceFieldHandler = new FieldHandler<>(newEntityInstance);
        newInstanceFieldHandler.entitySetterValues(RevisionField::getNewValue, getRevisedFields());

        revisedEntity.put(RevisionConsts.CURRENT_ENTITY, currentEntityInstance);
        revisedEntity.put(RevisionConsts.NEW_ENTITY, newEntityInstance);
        return revisedEntity;
    }

    public List<RevisionField> getRevisedFields() {
        return getFieldRevisions().stream().filter(RevisionField::isChanged).toList();
    }

    public List<RevisionField> getFieldRevisions() {
        List<RevisionField> revisionFields = new ArrayList<>();

        FieldHandler<T> oldEntityFields = new FieldHandler<>(currentEntity);
        FieldHandler<T> newEntityFields = new FieldHandler<>(newEntity);
        oldEntityFields.toHashMap().forEach((fieldName, currentValue) -> {
            Object newValue = newEntityFields.toHashMap().get(fieldName);

            RevisionField revisionField = new RevisionField();
            revisionField.setField(fieldName);
            revisionField.setCurrentValue(currentValue);
            revisionField.setNewValue(newValue);
            revisionField.checkIfChanged();

            revisionFields.add(revisionField);
        });

        return revisionFields;
    }


}

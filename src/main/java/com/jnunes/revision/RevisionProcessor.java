package com.jnunes.revision;

import com.jnunes.revision.vo.EntityField;
import com.jnunes.revision.vo.RevisionField;
import com.jnunes.revision.vo.RevisionValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RevisionProcessor<T> {

    private final T currentEntity;
    private final T updatedEntity;
    private boolean onlyUpdated;

    private RevisionProcessor(T currentEntity, T updatedEntity) {
        this.currentEntity = currentEntity;
        this.updatedEntity = updatedEntity;
    }

    public static <T> RevisionProcessor<T> of(T currentEntity, T updatedEntity) {
        return new RevisionProcessor<>(currentEntity, updatedEntity);
    }

    public RevisionProcessor<T> onlyUpdated() {
        this.onlyUpdated = true;
        return this;
    }

    public List<RevisionField<T>> build() {
        return onlyUpdated ? buildOnlyUpdated() : internalBuild();
    }

    private List<RevisionField<T>> buildOnlyUpdated() {
        return internalBuild().stream().filter(it -> it.getRevisionValue().isChanged()).toList();
    }

    private List<RevisionField<T>> internalBuild() {
        List<EntityField<T>> currentEntityFields = FieldHandler.of(currentEntity).build();
        List<EntityField<T>> updatedEntityFields = FieldHandler.of(updatedEntity).build();

        List<RevisionField<T>> revisionFields = new ArrayList<>();

        currentEntityFields.forEach(currentField -> {
            EntityField<T> updatedField = getUpdatedField(currentField.getName(), updatedEntityFields);
            RevisionField<T> revisionField = createRevisionField(currentField, updatedField);

            revisionFields.add(revisionField);
        });

        return revisionFields;
    }

    private EntityField<T> getUpdatedField(String name, List<EntityField<T>> newEntityFields) {
        Predicate<EntityField<T>> equalsName = entityField -> Objects.equals(name, entityField.getName());

        return newEntityFields.stream().filter(equalsName).findFirst()
                .orElseThrow(() -> new RevisionException("Field not found exception."));
    }

    private RevisionField<T> createRevisionField(EntityField<T> currentField, EntityField<T> updatedField) {
        RevisionValue<T> revisionValue = new RevisionValue<>();
        revisionValue.setCurrent(currentField.getValue());
        revisionValue.setUpdated(updatedField.getValue());
        revisionValue.checkIfChanged();

        RevisionField<T> revisionField = new RevisionField<>();
        revisionField.setName(currentField.getName());
        revisionField.setRevisionValue(revisionValue);
        return revisionField;
    }

}

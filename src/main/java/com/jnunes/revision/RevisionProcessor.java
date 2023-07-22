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

    public List<RevisionField> build() {
        return onlyUpdated ? buildOnlyUpdated() : internalBuild();
    }

    private List<RevisionField> buildOnlyUpdated() {
        return internalBuild().stream().filter(it -> it.getRevisionValue().isChanged()).toList();
    }

    private List<RevisionField> internalBuild() {
        List<EntityField> currentEntityFields = FieldHandler.of(currentEntity).build();
        List<EntityField> updatedEntityFields = FieldHandler.of(updatedEntity).build();

        List<RevisionField> revisionFields = new ArrayList<>();

        currentEntityFields.forEach(currentField -> {
            EntityField updatedField = getUpdatedField(currentField.getName(), updatedEntityFields);
            RevisionField revisionField = createRevisionField(currentField, updatedField);

            revisionFields.add(revisionField);
        });

        return revisionFields;
    }

    private EntityField getUpdatedField(String name, List<EntityField> newEntityFields) {
        Predicate<EntityField> equalsName = entityField -> Objects.equals(name, entityField.getName());

        return newEntityFields.stream().filter(equalsName).findFirst()
                .orElseThrow(() -> new RevisionException("Field not found exception."));
    }

    private RevisionField createRevisionField(EntityField currentField, EntityField updatedField) {
        RevisionValue revisionValue = new RevisionValue();
        revisionValue.setCurrent(currentField.getValue());
        revisionValue.setUpdated(updatedField.getValue());
        revisionValue.checkIfChanged();

        RevisionField revisionField = new RevisionField();
        revisionField.setName(currentField.getName());
        revisionField.setRevisionValue(revisionValue);
        return revisionField;
    }

}

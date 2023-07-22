package com.jnunes.revision;

import com.jnunes.revision.vo.EntityField;
import com.jnunes.revision.vo.RevisionField;
import com.jnunes.revision.vo.RevisionValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class FieldProcessor<T> {
    private final List<EntityField<T>> currentEntityFields;
    private final List<EntityField<T>> newEntityFields;


    private FieldProcessor(List<EntityField<T>> currentEntityFields, List<EntityField<T>> newEntityFields) {
        this.currentEntityFields = currentEntityFields;
        this.newEntityFields = newEntityFields;
    }

    public static <F> FieldProcessor<F> of(List<EntityField<F>> currentEntityFields, List<EntityField<F>> newEntityFields) {
        return new FieldProcessor<>(currentEntityFields, newEntityFields);
    }

    public List<RevisionField<T>> build() {
        List<RevisionField<T>> revisionFields = new ArrayList<>();

        currentEntityFields.stream().map(this::createRevisionField).forEach(revisionFields::add);
        return revisionFields;
    }

    private RevisionField<T> createRevisionField(EntityField<T> entityField) {
        EntityField<T> newEntityField = getNewEntityField(entityField.getName(), newEntityFields);

        RevisionValue<T> revisionValue = new RevisionValue<>();
        revisionValue.setCurrent(entityField.getValue());
        revisionValue.setUpdated(newEntityField.getValue());
        revisionValue.checkIfChanged();

        RevisionField<T> revisionField = new RevisionField<>();
        revisionField.setName(entityField.getName());
        revisionField.setRevisionValue(revisionValue);
        return revisionField;
    }

    private EntityField<T> getNewEntityField(String name, List<EntityField<T>> newEntityFields) {
        Predicate<EntityField<T>> equalsName = entityField -> Objects.equals(name, entityField.getName());

        return newEntityFields.stream().filter(equalsName).findFirst()
                .orElseThrow(() -> new RevisionException("Field not found exception."));
    }
}

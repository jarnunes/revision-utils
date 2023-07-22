package com.jnunes.revision;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RevisionFieldProcessor {
    private final List<EntityField> currentEntityFields;
    private final List<EntityField> newEntityFields;


    private RevisionFieldProcessor(List<EntityField> currentEntityFields, List<EntityField> newEntityFields) {
        this.currentEntityFields = currentEntityFields;
        this.newEntityFields = newEntityFields;
    }

    public static RevisionFieldProcessor of(List<EntityField> currentEntityFields, List<EntityField> newEntityFields) {
        return new RevisionFieldProcessor(currentEntityFields, newEntityFields);
    }

    public List<RevisionField> build() {
        List<RevisionField> revisionFields = new ArrayList<>();

        currentEntityFields.stream().map(this::createRevisionField).forEach(revisionFields::add);
        return revisionFields;
    }

    private RevisionField createRevisionField(EntityField entityField) {
        EntityField newEntityField = getNewEntityField(entityField.getName(), newEntityFields);

        RevisionField revisionField = new RevisionField();
        revisionField.setFieldName(entityField.getName());
        revisionField.setCurrentValue(entityField.getValue());
        revisionField.setNewValue(newEntityField.getValue());
        revisionField.checkIfChanged();
        return revisionField;
    }

    private EntityField getNewEntityField(String name, List<EntityField> newEntityFields) {
        Predicate<EntityField> equalsName = entityField -> Objects.equals(name, entityField.getName());

        return newEntityFields.stream().filter(equalsName).findFirst()
                .orElseThrow(() -> new RevisionException("Field not found exception."));
    }
}

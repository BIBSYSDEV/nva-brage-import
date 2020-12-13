package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.RelationType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageRelation {
    private final RelationType relationType;
    private final String value;

    public BrageRelation(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageRelation(String qualifier, String value) throws InvalidQualifierException {
        this(RelationType.getTypeByName(qualifier), value);
    }

    public BrageRelation(RelationType relationType, String value) {
        this.relationType = relationType;
        this.value = value;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public String getValue() {
        return value;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageRelation)) {
            return false;
        }
        BrageRelation that = (BrageRelation) o;
        return getRelationType() == that.getRelationType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getRelationType(), getValue());
    }
}

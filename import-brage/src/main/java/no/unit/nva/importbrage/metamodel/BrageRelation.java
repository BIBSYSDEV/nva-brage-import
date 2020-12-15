package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.RelationType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageRelation extends BrageLanguageValue {
    private final RelationType relationType;

    public BrageRelation(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageRelation(String qualifier, String value, String language) throws InvalidQualifierException {
        this(RelationType.getTypeByName(qualifier), value, language);
    }

    public BrageRelation(RelationType relationType, String value, String language) {
        super(value, language);
        this.relationType = relationType;
    }

    public RelationType getRelationType() {
        return relationType;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageRelation that = (BrageRelation) o;
        return getRelationType() == that.getRelationType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRelationType());
    }
}

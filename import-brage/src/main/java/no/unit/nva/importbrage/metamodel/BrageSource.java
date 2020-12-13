package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageSource extends BrageLanguageValue {
    private final SourceType sourceType;

    public BrageSource(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageSource(String qualifier, String value, String language) throws InvalidQualifierException {
        this(SourceType.getTypeByName(qualifier), value, language);
    }

    public BrageSource(SourceType sourceType, String value, String language) {
        super(value, language);
        this.sourceType = sourceType;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageSource)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageSource that = (BrageSource) o;
        return getSourceType() == that.getSourceType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSourceType());
    }
}

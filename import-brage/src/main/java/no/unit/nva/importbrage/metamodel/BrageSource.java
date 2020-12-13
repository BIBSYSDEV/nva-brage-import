package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.SourceType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageSource {
    private final SourceType sourceType;
    private final String value;

    public BrageSource(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageSource(String qualifier, String value) throws InvalidQualifierException {
        this(SourceType.getTypeByName(qualifier), value);
    }

    public BrageSource(SourceType sourceType, String value) {
        this.sourceType = sourceType;
        this.value = value;
    }

    public SourceType getSourceType() {
        return sourceType;
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
        if (!(o instanceof BrageSource)) {
            return false;
        }
        BrageSource that = (BrageSource) o;
        return getSourceType() == that.getSourceType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getSourceType(), getValue());
    }
}

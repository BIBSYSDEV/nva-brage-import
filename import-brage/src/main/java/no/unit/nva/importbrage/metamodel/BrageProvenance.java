package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.ProvenanceType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageProvenance {
    private final ProvenanceType provenanceType;
    private final String value;

    public BrageProvenance(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageProvenance(String provenanceType, String value) throws InvalidQualifierException {
        this(ProvenanceType.getTypeByName(provenanceType), value);
    }

    public BrageProvenance(ProvenanceType provenanceType, String value) {
        this.provenanceType = provenanceType;
        this.value = value;
    }

    public ProvenanceType getProvenanceType() {
        return provenanceType;
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
        if (!(o instanceof BrageProvenance)) {
            return false;
        }
        BrageProvenance that = (BrageProvenance) o;
        return getProvenanceType() == that.getProvenanceType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getProvenanceType(), getValue());
    }
}

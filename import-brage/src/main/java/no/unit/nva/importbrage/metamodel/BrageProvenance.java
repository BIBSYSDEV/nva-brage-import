package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.ProvenanceType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageProvenance extends BrageLanguageValue {
    private final ProvenanceType provenanceType;

    public BrageProvenance(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageProvenance(String provenanceType, String value, String language) throws InvalidQualifierException {
        this(ProvenanceType.getTypeByName(provenanceType, value), value, language);
    }

    public BrageProvenance(ProvenanceType provenanceType, String value, String laanguage) {
        super(value, laanguage);
        this.provenanceType = provenanceType;
    }

    public ProvenanceType getProvenanceType() {
        return provenanceType;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageProvenance that = (BrageProvenance) o;
        return getProvenanceType() == that.getProvenanceType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProvenanceType());
    }
}

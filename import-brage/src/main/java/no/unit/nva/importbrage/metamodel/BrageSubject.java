package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.SubjectType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageSubject {

    private final SubjectType subjectType;
    private final String value;

    public BrageSubject(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageSubject(String qualifier, String value) throws InvalidQualifierException {
        this(SubjectType.getTypeByName(qualifier), value);
    }

    public BrageSubject(SubjectType subjectType, String value) {
        this.subjectType = subjectType;
        this.value = value;
    }

    public SubjectType getSubjectType() {
        return subjectType;
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
        if (!(o instanceof BrageSubject)) {
            return false;
        }
        BrageSubject that = (BrageSubject) o;
        return getSubjectType() == that.getSubjectType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getSubjectType(), getValue());
    }
}

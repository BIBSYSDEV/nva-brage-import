package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.SubjectType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageSubject extends BrageLanguageValue {

    private final SubjectType subjectType;

    public BrageSubject(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageSubject(String qualifier, String value, String language) throws InvalidQualifierException {
        this(SubjectType.getTypeByName(qualifier, value), value, language);
    }

    public BrageSubject(SubjectType subjectType, String value, String language) {
        super(value, language);
        this.subjectType = subjectType;
    }

    public SubjectType getSubjectType() {
        return subjectType;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageSubject that = (BrageSubject) o;
        return getSubjectType() == that.getSubjectType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSubjectType());
    }
}

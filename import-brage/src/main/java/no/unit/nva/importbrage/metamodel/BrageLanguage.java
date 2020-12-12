package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.LanguageType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageLanguage {
    private final LanguageType languageType;
    private final String value;

    public BrageLanguage(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageLanguage(String languageType, String value) throws InvalidQualifierException {
        this(LanguageType.getTypeByName(languageType), value);
    }

    public BrageLanguage(LanguageType languageType, String value) {
        this.languageType = languageType;
        this.value = value;
    }

    public LanguageType getLanguageType() {
        return languageType;
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
        if (!(o instanceof BrageLanguage)) {
            return false;
        }
        BrageLanguage that = (BrageLanguage) o;
        return getLanguageType() == that.getLanguageType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getLanguageType(), getValue());
    }
}

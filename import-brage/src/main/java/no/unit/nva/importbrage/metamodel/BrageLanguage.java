package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.LanguageType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageLanguage extends BrageLanguageValue {
    private final LanguageType languageType;

    public BrageLanguage(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageLanguage(String languageType, String value, String language) throws InvalidQualifierException {
        this(LanguageType.getTypeByName(languageType), value, language);
    }

    public BrageLanguage(LanguageType languageType, String value, String language) {
        super(value, language);
        this.languageType = languageType;
    }

    public LanguageType getLanguageType() {
        return languageType;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageLanguage that = (BrageLanguage) o;
        return getLanguageType() == that.getLanguageType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLanguageType());
    }
}

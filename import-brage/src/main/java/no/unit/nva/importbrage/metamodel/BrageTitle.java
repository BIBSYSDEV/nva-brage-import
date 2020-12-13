package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.TitleType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageTitle extends BrageLanguageValue {
    private final TitleType titleType;

    public BrageTitle(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageTitle(String qualifier, String value, String language) throws InvalidQualifierException {
        this(TitleType.getTypeByName(qualifier), value, language);
    }

    public BrageTitle(TitleType titleType, String value, String language) {
        super(value, language);
        this.titleType = titleType;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageTitle)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageTitle that = (BrageTitle) o;
        return getTitleType() == that.getTitleType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitleType());
    }
}

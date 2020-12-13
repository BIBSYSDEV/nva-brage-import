package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.TitleType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageTitle {
    private final TitleType titleType;
    private final String value;

    public BrageTitle(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageTitle(String qualifier, String value) throws InvalidQualifierException {
        this(TitleType.getTypeByName(qualifier), value);
    }

    public BrageTitle(TitleType titleType, String value) {
        this.titleType = titleType;
        this.value = value;
    }

    public TitleType getTitleType() {
        return titleType;
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
        if (!(o instanceof BrageTitle)) {
            return false;
        }
        BrageTitle that = (BrageTitle) o;
        return getTitleType() == that.getTitleType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getTitleType(), getValue());
    }
}

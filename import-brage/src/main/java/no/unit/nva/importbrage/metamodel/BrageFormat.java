package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.FormatType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

import static no.unit.nva.importbrage.metamodel.types.FormatType.EXTENT;

public class BrageFormat extends BrageValue {
    private final FormatType formatType;

    public BrageFormat(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageFormat(String formatType, String value) throws InvalidQualifierException {
        this(FormatType.getTypeByTypeName(formatType, value), value);
    }

    public BrageFormat(FormatType formatType, String value) {
        super(value);
        this.formatType = formatType;
    }

    public boolean isExtent() {
        return EXTENT.equals(formatType);
    }

    public FormatType getFormatType() {
        return formatType;
    }

    @JacocoGenerated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrageFormat)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BrageFormat that = (BrageFormat) o;
        return getFormatType() == that.getFormatType();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFormatType());
    }
}

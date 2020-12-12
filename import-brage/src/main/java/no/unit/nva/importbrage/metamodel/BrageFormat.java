package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.FormatType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageFormat {
    private final FormatType formatType;
    private final String value;

    public BrageFormat(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageFormat(String formatType, String value) throws InvalidQualifierException {
        this(FormatType.getTypeByTypeName(formatType), value);
    }

    public BrageFormat(FormatType formatType, String value) {
        this.formatType = formatType;
        this.value = value;
    }

    public FormatType getFormatType() {
        return formatType;
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
        if (!(o instanceof BrageFormat)) {
            return false;
        }
        BrageFormat that = (BrageFormat) o;
        return getFormatType() == that.getFormatType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getFormatType(), getValue());
    }
}

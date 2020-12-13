package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageType {
    private final TypeBasic typeBasic;
    private final String value;

    public BrageType(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageType(String qualifier, String value) throws InvalidQualifierException {
        this(TypeBasic.getTypeByName(qualifier), value);
    }

    public BrageType(TypeBasic typeBasic, String value) {
        this.typeBasic = typeBasic;
        this.value = value;
    }

    public TypeBasic getTypeBasic() {
        return typeBasic;
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
        if (!(o instanceof BrageType)) {
            return false;
        }
        BrageType brageType = (BrageType) o;
        return getTypeBasic() == brageType.getTypeBasic()
                && Objects.equals(getValue(), brageType.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getTypeBasic(), getValue());
    }
}

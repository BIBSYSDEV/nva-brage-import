package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.RightsType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageRights {
    private final RightsType rightsType;
    private final String value;

    public BrageRights(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue());
    }

    public BrageRights(String rightsType, String value) throws InvalidQualifierException {
        this(RightsType.getTypeByName(rightsType), value);
    }

    public BrageRights(RightsType rightsType, String value) {
        this.rightsType = rightsType;
        this.value = value;
    }

    public RightsType getRightsType() {
        return rightsType;
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
        if (!(o instanceof BrageRights)) {
            return false;
        }
        BrageRights that = (BrageRights) o;
        return getRightsType() == that.getRightsType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getRightsType(), getValue());
    }
}

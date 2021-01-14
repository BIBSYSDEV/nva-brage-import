package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.RightsType;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageRights extends BrageLanguageValue {
    private final RightsType rightsType;

    public BrageRights(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageRights(String rightsType, String value, String language) throws InvalidQualifierException {
        this(RightsType.getTypeByName(rightsType, value), value, language);
    }

    public BrageRights(RightsType rightsType, String value, String language) {
        super(value, language);
        this.rightsType = rightsType;
    }

    public RightsType getRightsType() {
        return rightsType;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageRights that = (BrageRights) o;
        return getRightsType() == that.getRightsType()
                && Objects.equals(getValue(), that.getValue());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRightsType(), getValue());
    }
}

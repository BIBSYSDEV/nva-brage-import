package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.DcValue;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

public class BrageType extends BrageLanguageValue {
    private final TypeBasic typeBasic;

    public BrageType(DcValue value) throws InvalidQualifierException {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageType(String qualifier, String value, String language) throws InvalidQualifierException {
        this(TypeBasic.getTypeByName(qualifier), value, language);
    }

    public BrageType(TypeBasic typeBasic, String value, String language) {
        super(value, language);
        this.typeBasic = typeBasic;
    }

    public TypeBasic getTypeBasic() {
        return typeBasic;
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
        if (!super.equals(o)) {
            return false;
        }
        BrageType brageType = (BrageType) o;
        return getTypeBasic() == brageType.getTypeBasic();
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTypeBasic());
    }
}

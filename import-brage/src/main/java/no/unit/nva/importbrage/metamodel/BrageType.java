package no.unit.nva.importbrage.metamodel;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import no.unit.nva.importbrage.metamodel.types.TypeValue;
import nva.commons.utils.JacocoGenerated;

import java.util.Objects;

import static no.unit.nva.importbrage.metamodel.types.TypeValue.PEER_REVIEWED;
import static no.unit.nva.importbrage.metamodel.types.TypeValue.PREPRINT;

public class BrageType {
    private final TypeBasic typeBasic;
    private final TypeValue value;
    private final String language;

    public BrageType(DcValue value) throws Exception {
        this(value.getQualifier(), value.getValue(), value.getLanguage());
    }

    public BrageType(String qualifier, String value, String language) throws Exception {
        this(TypeBasic.getTypeByName(qualifier, value), TypeValue.getTypeValueByName(value), language);
    }

    public BrageType(TypeBasic typeBasic, TypeValue value, String language) {
        this.typeBasic = typeBasic;
        this.value = value;
        this.language = language;
    }

    public boolean isPeerReviewed() {
        return PEER_REVIEWED.equals(value);
    }

    public boolean isPreprint() {
        return PREPRINT.equals(value);
    }

    @JacocoGenerated
    public TypeBasic getTypeBasic() {
        return typeBasic;
    }

    public TypeValue getValue() {
        return value;
    }

    @JacocoGenerated
    public String getLanguage() {
        return language;
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
                && getValue() == brageType.getValue()
                && Objects.equals(getLanguage(), brageType.getLanguage());
    }

    @JacocoGenerated
    @Override
    public int hashCode() {
        return Objects.hash(getTypeBasic(), getValue(), getLanguage());
    }
}

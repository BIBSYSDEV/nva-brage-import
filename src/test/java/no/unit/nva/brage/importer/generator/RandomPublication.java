package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.TypeBasic;
import no.unit.nva.importbrage.metamodel.types.TypeValue;

import java.util.ArrayList;
import java.util.List;

public class RandomPublication {
    public static final String EN_US = "en_US";
    private final List<DcValue> dcValues = new ArrayList<>();


    protected void addDcValue(String element, String type, String language, String value) {
        var dcValue = new DcValueBuilder()
                .withElement(element)
                .withLanguage(language)
                .withQualifier(type)
                .withValue(value)
                .build();
        dcValues.add(dcValue);
    }

    protected void addDcValue(DcValue dcValue) {
        dcValues.add(dcValue);
    }

    protected void setDcValueType(TypeValue value) {
        addDcValue(TypeBasic.TYPE, TypeBasic.UNQUALIFIED.getQualifier(), EN_US, value.getTypeName());
    }

    public List<DcValue> getDcValues() {
        return dcValues;
    }
}

package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;

public interface DescriptionTypes {
    String EN_US = "en_US";

    DcValue getDcValue();

    default DcValue getDcValue(String description, DescriptionType type) {
        return new DcValueBuilder()
                .withElement(DescriptionType.DESCRIPTION)
                .withLanguage(EN_US)
                .withQualifier(type.getQualifier())
                .withValue(description)
                .build();
    }
}

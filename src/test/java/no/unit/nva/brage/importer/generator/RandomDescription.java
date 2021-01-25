package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;

public class RandomDescription implements DescriptionTypes {

    private final String description;
    private final DcValue dcValue;

    public RandomDescription() {
        this.description = generateRandomDescription();
        this.dcValue = getDcValue(description, DescriptionType.UNQUALIFIED);
    }

    private RandomDescription(String description, DcValue dcValue) {
        this.description = description;
        this.dcValue = dcValue;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public DcValue getDcValue() {
        return dcValue;
    }

    private String generateRandomDescription() {
        return "猫 is a good word in a description";
    }
}

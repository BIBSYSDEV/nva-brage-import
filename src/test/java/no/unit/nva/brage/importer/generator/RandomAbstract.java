package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.importbrage.metamodel.types.DescriptionType;

public class RandomAbstract implements DescriptionTypes {

    private final String abztract;
    private final DcValue dcValue;

    public RandomAbstract() {
        this.abztract = generateRandomDescription();
        this.dcValue = getDcValue(abztract, DescriptionType.ABSTRACT);
    }

    private RandomAbstract(String abztract, DcValue dcValue) {
        this.abztract = abztract;
        this.dcValue = dcValue;
    }

    public String getAbstract() {
        return abztract;
    }

    @Override
    public DcValue getDcValue() {
        return dcValue;
    }

    private String generateRandomDescription() {
        return "猫 is a good word in a description";
    }
}

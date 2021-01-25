package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.CreatorType;
import no.unit.nva.model.Contributor;
import no.unit.nva.model.Identity;
import no.unit.nva.model.NameType;
import no.unit.nva.model.Role;
import no.unit.nva.model.exceptions.MalformedContributorException;

import java.util.List;
import java.util.Random;

public class RandomCreator {

    public static final Random RANDOM = new Random();
    public static final int MAXIMUM_SEVEN = 7;
    public static final String INVERTED_NAME_TEMPLATE = "%s, %s";
    public static final List<String> FIRST_NAMES = List.of("Ainna", "Binna", "Cinna", "Dinna", "Einna", "Finna",
            "Ginna", "Hinna");
    public static final List<String> SECOND_NAMES = List.of("Aisley", "Bisley", "Cisley", "Disley", "Eisley", "Fisley",
            "Gisley", "Hisley");
    public static final String EN_US = "en_US";

    private final String name;

    public RandomCreator() {
        this.name = randomName();
    }

    /**
     * Generates an NVA Contributor with assigned random values.
     * @return An NVA Contributor.
     * @throws MalformedContributorException If the contributor cannot be created.
     */
    public Contributor generateContributor() throws MalformedContributorException {
        return new Contributor.Builder()
                .withIdentity(getIdentity(NameType.PERSONAL, name))
                .withRole(Role.CREATOR)
                .build();
    }

    /**
     * Returns equivalent DcValues for a creator.
     * @return A list of DcValues.
     */
    public DcValue generateDcValuesForCreator() {
        return new DcValueBuilder()
                .withElement(CreatorType.CREATOR)
                .withLanguage(EN_US)
                .withQualifier(CreatorType.UNQUALIFIED.getQualifier())
                .withValue(this.name)
                .build();
    }

    private String randomName() {
        return String.format(INVERTED_NAME_TEMPLATE,
                SECOND_NAMES.get(getOneOfEight()),
                FIRST_NAMES.get(getOneOfEight()));
    }
    private int getOneOfEight() {
        return RANDOM.nextInt(MAXIMUM_SEVEN);
    }

    private Identity getIdentity(NameType nameType, String name) {
        return new Identity.Builder()
                .withNameType(nameType)
                .withName(name)
                .withId(null)
                .build();
    }
}

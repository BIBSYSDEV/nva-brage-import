package no.unit.nva.brage.importer.generator;

import no.unit.nva.brage.dublincore.DcValue;
import no.unit.nva.brage.importer.DcValueBuilder;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.model.Contributor;
import no.unit.nva.model.Identity;
import no.unit.nva.model.NameType;
import no.unit.nva.model.Role;
import no.unit.nva.model.exceptions.MalformedContributorException;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.Objects.nonNull;

public class RandomContributor {

    public static final Random RANDOM = new Random();
    public static final int MAXIMUM_SEVEN = 7;
    public static final String INVERTED_NAME_TEMPLATE = "%s, %s";
    public static final List<String> FIRST_NAMES = List.of("Aarry", "Barry", "Cerry", "Derry", "Eerry", "Ferry", "Gerry", "Herry");
    public static final List<String> SECOND_NAMES = List.of("Aaston", "Baston", "Caston", "Daston", "Easton", "Faston", "Gaston", "Haston");
    public static final int MAXIMUM_TWO = 2;
    public static final List<Role> ROLES = List.of(Role.ADVISOR, Role.CREATOR, Role.ILLUSTRATOR);
    public static final String EN_US = "en_US";

    private final String name;
    private final String orcid;
    private final Role role;

    public RandomContributor(boolean withOrcid) {
        this.name = randomName();
        this.orcid = withOrcid ? randomOrcid() : null;
        this.role = randomRole();
    }

    /**
     * In cases where the desired result is a particular role.
     * @param role E.g. Role.EDITOR
     */
    public RandomContributor(Role role) {
        this.name = randomName();
        this.orcid = randomOrcid();
        this.role = role;
    }

    /**
     * Generates an NVA contributor with assigned random values.
     * @return An NVA Contributor.
     * @throws MalformedContributorException If the contributor cannot be created.
     */
    public Contributor generateContributor() throws MalformedContributorException {
        return new Contributor.Builder()
                .withIdentity(getIdentity(NameType.PERSONAL, name, orcid))
                .withRole(role)
                .build();
    }

    /**
     * Returns equivalent DcValues for a contributor.
     * @return A list of DcValues.
     */
    public List<DcValue> generateDcValuesForContributor() {
        return nonNull(orcid) ? List.of(getOrcid(), getName()) : List.of(getName());
    }

    public DcValue getName() {
        return new DcValueBuilder()
                .withElement(ContributorType.CONTRIBUTOR)
                .withLanguage(EN_US)
                .withQualifier(ContributorType.getTypeByRole(role).getQualifier())
                .withValue(name)
                .build();
    }

    private DcValue getOrcid() {
        return new DcValueBuilder()
                .withElement(ContributorType.CONTRIBUTOR)
                .withLanguage(EN_US)
                .withQualifier(ContributorType.ORCID.getQualifier())
                .withValue(orcid)
                .build();
    }

    private String randomOrcid() {
        return UUID.randomUUID().toString();
    }

    private String randomName() {
        return String.format(INVERTED_NAME_TEMPLATE,
                SECOND_NAMES.get(getOneOfEight()),
                FIRST_NAMES.get(getOneOfEight()));
    }
    private int getOneOfFour() {
        return RANDOM.nextInt(MAXIMUM_TWO);
    }
    private int getOneOfEight() {
        return RANDOM.nextInt(MAXIMUM_SEVEN);
    }

    private Identity getIdentity(NameType nameType, String name, String orcid) {
        return new Identity.Builder()
                .withNameType(nameType)
                .withName(name)
                .withOrcId(orcid)
                .withId(null)
                .build();
    }

    /**
     * Excludes EDITOR as this triggers side effects for Book-types.
     * @return A Role, excluding EDITOR.
     */
    private Role randomRole() {
        return ROLES.get(getOneOfFour());
    }

}
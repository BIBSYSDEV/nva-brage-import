package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum ContributorType implements ElementType {
    ADVISOR("advisor"),
    AUTHOR("author"),
    DEPARTMENT("department"),
    EDITOR("editor"),
    ILLUSTRATOR("illustrator"),
    ORCID("orcid"),
    OTHER("other"),
    UNQUALIFIED(null);

    public static final String CONTRIBUTOR = "contributor";
    private final String typeName;

    ContributorType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent ContributorType by its string representation.
     *
     * @param candidate A string of a ContributorType.
     * @return A corresponding ContributorType
     */
    public static ContributorType getTypeByName(String candidate) throws InvalidQualifierException {
        return (ContributorType) ElementType.getTypeByName(CONTRIBUTOR, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}

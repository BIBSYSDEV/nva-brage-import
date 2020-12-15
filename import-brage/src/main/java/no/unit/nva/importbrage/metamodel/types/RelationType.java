package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum RelationType implements ElementType {
    HAS_PART("haspart", true),
    IS_PART_OF("ispartof", true),
    IS_PART_OF_SERIES("ispartofseries", false),
    PROJECT("project", true),
    URI("uri", false),
    UNQUALIFIED(null, false);

    public static final String RELATION = "relation";
    private final String typeName;
    private final boolean languageBased;

    RelationType(String typeName, boolean languageBased) {
        this.typeName = typeName;
        this.languageBased = languageBased;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean isLanguageBased() {
        return this.languageBased;
    }

    /**
     * Get the equivalent RelationType by its string representation.
     *
     * @param candidate A string of a RelationType.
     * @return A corresponding RelationType
     */
    public static RelationType getTypeByName(String candidate) throws InvalidQualifierException {
        return (RelationType) ElementType.getTypeByName(RELATION, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}

package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum RelationType implements ElementType {
    HAS_PART("haspart"),
    IS_PART_OF("ispartof"),
    IS_PART_OF_SERIES("ispartofseries"),
    PROJECT("project"),
    URI("uri"),
    UNQUALIFIED(null);

    public static final String RELATION = "relation";
    private final String typeName;

    RelationType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
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

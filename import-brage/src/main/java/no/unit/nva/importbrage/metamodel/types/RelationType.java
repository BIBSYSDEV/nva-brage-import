package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum RelationType implements ElementType {
    HAS_PART("haspart"),
    IS_PART_OF("ispartof"),
    IS_PART_OF_SERIES("ispartofseries"),
    PROJECT("project"),
    URI("uri"),
    UNQUALIFIED("none");

    public static final String RELATION = "relation";
    private final String typeName;

    RelationType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return RELATION;
    }

    @Override
    public ElementType[] getValues() {
        return values();
    }

    @Override
    public String getQualifier() {
        return typeName;
    }

    /**
     * Get the equivalent RelationType by its string representation.
     *
     * @param candidate A string of a RelationType.
     * @return A corresponding RelationType
     */
    public static RelationType getTypeByName(String candidate, String value) throws InvalidQualifierException {
        return (RelationType) ElementType.getTypeByName(RELATION, candidate, values(), UNQUALIFIED, value);
    }
}

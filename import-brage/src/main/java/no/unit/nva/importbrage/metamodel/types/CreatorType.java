package no.unit.nva.importbrage.metamodel.types;

import no.unit.nva.importbrage.metamodel.exceptions.InvalidQualifierException;

public enum CreatorType implements ElementType {
    UNQUALIFIED(null);

    public static final String CREATOR = "creator";
    private final String typeName;

    CreatorType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Get the equivalent CreatorType by its string representation.
     *
     * @param candidate A string of a CreatorType.
     * @return A corresponding CreatorType
     */
    public static CreatorType getTypeByName(String candidate) throws InvalidQualifierException {
        return (CreatorType) ElementType.getTypeByName(CREATOR, candidate, values(), UNQUALIFIED);
    }

    /**
     * Generates a string representation of the allowed type values.
     * @return A string representation of the allowed values.
     */
    public static String getAllowedValues() {
        return ElementType.getAllowedValues(values());
    }
}
